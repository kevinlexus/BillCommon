package com.ric.cmn;

import com.ric.cmn.excp.WrongParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;

/**
 * Утилиты
 *
 * @author lev
 * @version 1.01
 */
@Slf4j
public class Utl {


    /**
     * Аналог SQL IN
     *
     * @param value - значение
     * @param list  - список
     * @return - находится в списке?
     */
    public static <T> boolean in(T value, @SuppressWarnings("unchecked") T... list) {
        for (T item : list) {
            if (value.equals(item))
                return true;
        }
        return false;
    }

    /**
     * Аналог LTRIM в Oracle
     *
     * @param str - исходная строка
     * @param chr - усекаемый символ
     * @return - усеченная слева строка
     */
    public static String ltrim(String str, String chr) {
        return str.replaceFirst("^" + chr + "+", "");
    }

    /**
     * Аналог LPAD в Oracle
     *
     * @param str - исходная строка
     * @param chr - символ, для добавления
     * @param cnt - кол-во символов
     * @return - строка с дополненными символами слева
     */
    public static String lpad(String str, String chr, Integer cnt) {
        return StringUtils.leftPad(str, cnt, chr);
    }

    /**
     * сравнить два параметра, с учётом их возможного null
     *
     * @param a - 1 значение
     * @param b - 2 значение
     * @return
     */
    public static <T> boolean cmp(T a, T b) {
        if (a == null && b == null) {
            return true;
        } else if (a != null && b != null) {
            if (a.getClass() == BigDecimal.class) {
                BigDecimal bd = (BigDecimal) a;
                if (bd.compareTo((BigDecimal) b) == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (a.equals(b)) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Вернуть второе значение, если первое пусто (аналог oracle NVL)
     *
     * @param a - 1 значение
     * @param b - 2 значение
     * @return
     */
    public static <T> T nvl(T a, T b) {
        return (a == null) ? b : a;
    }

    /**
     * Вернуть, если дата находится в диапазоне периода
     *
     * @param checkDt - проверяемая дата
     * @param dt1     - начало периода
     * @param dt2     - окончание периода
     * @return
     */
    public static boolean between(Date checkDt, Date dt1, Date dt2) {
        if (dt1 == null) {
            dt1 = getFirstDt();
        }
        if (dt2 == null) {
            dt2 = getLastDt();
        }

        if (checkDt.getTime() >= dt1.getTime() &&
                checkDt.getTime() <= dt2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // вернуть самую первую дату в биллинге
    public static Date getFirstDt() {
        Calendar calendar;
        calendar = new GregorianCalendar(1940, Calendar.JANUARY, 1);
        calendar.clear(Calendar.ZONE_OFFSET);
        return calendar.getTime();
    }

    // вернуть самую последнюю дату в биллинге
    public static Date getLastDt() {
        Calendar calendar;
        calendar = new GregorianCalendar(2940, Calendar.JANUARY, 1);
        calendar.clear(Calendar.ZONE_OFFSET);
        return calendar.getTime();
    }

    /**
     * вернуть true если хотя бы одна из дат находится в двух диапазонах периода
     *
     * @param checkDt1 - проверяемая дата
     * @param checkDt2 - проверяемая дата
     * @param dt1      - начало периода
     * @param dt2      - окончание периода
     * @return
     */
    public static boolean between2(Date checkDt1, Date checkDt2, Date dt1, Date dt2) {
        return between(checkDt1, dt1, dt2) || between(checkDt2, dt1, dt2);
    }

    /**
     * вернуть true если код находится в диапазоне
     *
     * @param checkNum - проверяемый код
     * @param numFrom  - начало диапазона
     * @param numTo    - окончание диапазона
     */
    public static boolean between2(String checkNum, String numFrom, String numTo) {
        Integer iCheckReu = Integer.parseInt(checkNum);
        Integer iReuFrom = Integer.parseInt(numFrom);
        Integer iReuTo = Integer.parseInt(numTo);
        int chk1 = iCheckReu.compareTo(iReuFrom);
        int chk2 = iCheckReu.compareTo(iReuTo);
        return chk1 >= 0 && chk2 <= 0;
    }

    /**
     * вернуть true если число находится в диапазоне
     *
     * @param checkId - проверяемое число
     * @param idFrom  - начало диапазона
     * @param idTo    - окончание диапазона
     * @return
     */
    public static boolean between2(int checkId, int idFrom, int idTo) {
        if (checkId >= idFrom && checkId <= idTo) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Находится ли одно BigDecimal в диапазоне двух других BigDecimal, включительно
     *
     * @param bd         - значение для поиска
     * @param rangeBegin - диапазон, начало
     * @param rangeEnd   - диапазон, окончание
     */
    public static boolean between(BigDecimal bd, BigDecimal rangeBegin, BigDecimal rangeEnd) {
        return (bd.compareTo(rangeBegin) >= 0 && bd.compareTo(rangeEnd) <= 0);
    }

    // вернуть кол-во лет между датами
    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    // вернуть кол-во месяцев между датами
    public static long getDiffMonths(Date first, Date last) {
        LocalDateTime dt1 = Instant.ofEpochMilli(first.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime dt2 = Instant.ofEpochMilli(last.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return ChronoUnit.MONTHS.between(dt1, dt2);
    }

    /**
     * Вернуть объект Calendar по заданной дате
     *
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    //вернуть случайный UUID
    public static UUID getRndUuid() {
        return UUID.randomUUID();
    }

    /**
     * Вернуть дату в XML типе
     *
     * @param dt
     * @return
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar getXMLDate(Date dt) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(dt);
        XMLGregorianCalendar xmlDt = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return xmlDt;
    }

    /**
     * Вернуть хост из строки URL
     *
     * @param urlStr - URL
     * @return хост-адрес
     * @throws UnknownHostException
     * @throws MalformedURLException
     */
    public static String getHostFromUrl(String urlStr) throws UnknownHostException, MalformedURLException {

        InetAddress address = InetAddress.getByName(new URL(urlStr).getHost());

        return address.getHostAddress();
    }

    /**
     * Вернуть путь из строки URL
     *
     * @param urlStr - URL
     * @return хост-адрес
     * @throws UnknownHostException
     * @throws MalformedURLException
     */
    public static String getPathFromUrl(String urlStr) throws UnknownHostException, MalformedURLException {

        return new URL(urlStr).getPath();
    }

    /**
     * Вернуть последнюю дату месяца
     *
     * @param dt - дата вх.
     * @return
     */
    public static Date getLastDate(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * Вернуть первую дату месяца
     *
     * @param dt - дата вх
     * @return
     */
    public static Date getFirstDate(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * Вернуть день из даты
     *
     * @param dt
     * @return
     */
    public static Integer getDay(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Вернуть дату по формату
     *
     * @param dt
     * @return
     */
    public static Date getDateFromStr(String dt) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        //try {
        date = formatter.parse(dt);

        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        return date;
    }

    /**
     * Конвертировать период ГГГГММ в дату
     *
     * @param period
     * @return
     */
    public static Date getDateFromPeriod(String period) throws ParseException {
        String str = "01" + "." + period.substring(4, 6) + "." + period.substring(0, 4);
        return getDateFromStr(str);
    }

    /**
     * Вернуть дату в виде строки по формату
     *
     * @param dt
     * @return
     */
    public static String getStrFromDate(Date dt) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String str = formatter.format(dt);
        return str;
    }

    /**
     * Вернуть дату в виде строки по определенному формату
     *
     * @param dt
     * @return
     */
    public static String getStrFromDate(Date dt, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format(dt);
        return str;
    }

    /**
     * Конвертировать XMLGregorianCalendar в Date
     *
     * @param cal
     * @return
     */
    public static Date getDateFromXmlGregCal(XMLGregorianCalendar cal) {
        if (cal != null) {
            return cal.toGregorianCalendar().getTime();
        } else {
            return null;
        }
    }

    /**
     * Получить % одного дня для заданного периода
     *
     * @return - % дня
     */
    public static double getPartDays(Date dt1, Date dt2) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        cal1.setTime(dt1);
        cal2.setTime(dt2);
        return 1 / (double) daysBetween(cal1.getTime(), cal2.getTime());
    }

    /**
     * Вернуть кол-во дней между двумя датами + 1
     *
     * @param dt1 - нач.дата
     * @param dt2 - кон.дата
     * @return - кол-во дней
     */
    public static int daysBetween(Date dt1, Date dt2) {
        return (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24) + 1);
    }

    /**
     * Заменить русские символы дней недели на английские
     *
     * @param str - вх. символы
     * @return - исх.символы
     */
    public static String convertDaysToEng(String str) {
        str = str.replaceAll("Пн", "Mon");
        str = str.replaceAll("Вт", "Tue");
        str = str.replaceAll("Ср", "Wed");
        str = str.replaceAll("Чт", "Thu");
        str = str.replaceAll("Пт", "Fri");
        str = str.replaceAll("Сб", "Sat");
        str = str.replaceAll("Вс", "Sun");
        return str;
    }

    /**
     * Конвертировать дату в YYYYMM
     *
     * @param dt - дата вх.
     * @return
     */
    public static String getPeriodFromDate(Date dt) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear(Calendar.ZONE_OFFSET);
        calendar.setTime(dt);
        String yy = String.valueOf(calendar.get(Calendar.YEAR));
        String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        mm = "0" + mm;
        mm = mm.substring(mm.length() - 2, mm.length());
        return yy + mm;
    }

    /**
     * Получить составляющую MM из строки период YYYYMM
     *
     * @param period
     */
    public static String getPeriodMonth(String period) {
        return period.substring(4, 6);
    }

    /**
     * Получить составляющую YYYY из строки период YYYYMM
     *
     * @param period
     */
    public static String getPeriodYear(String period) {
        return period.substring(0, 4);
    }

    /**
     * Конвертировать период ГГГГММ в наименование периода типа Апрель 2017, со склонением
     *
     * @param period
     * @param tp     - 0 - нач.период, 1 - кон.период
     * @return
     */
    public static String getPeriodName(String period, Integer tp) {
        String str = getMonthName(Integer.valueOf(period.substring(4, 6)), tp) + " " + period.substring(0, 4);
        return str;
    }

    public static String getMonthName(Integer month, Integer tp) {
        String monthString;
        if (tp == 0) {
            switch (month) {
                case 1:
                    monthString = "Января";
                    break;
                case 2:
                    monthString = "Февраля";
                    break;
                case 3:
                    monthString = "Марта";
                    break;
                case 4:
                    monthString = "Апреля";
                    break;
                case 5:
                    monthString = "Мая";
                    break;
                case 6:
                    monthString = "Июня";
                    break;
                case 7:
                    monthString = "Июля";
                    break;
                case 8:
                    monthString = "Августа";
                    break;
                case 9:
                    monthString = "Сентября";
                    break;
                case 10:
                    monthString = "Октября";
                    break;
                case 11:
                    monthString = "Ноября";
                    break;
                case 12:
                    monthString = "Декабря";
                    break;
                default:
                    monthString = null;
                    break;
            }
        } else {
            switch (month) {
                case 1:
                    monthString = "Январь";
                    break;
                case 2:
                    monthString = "Февраль";
                    break;
                case 3:
                    monthString = "Март";
                    break;
                case 4:
                    monthString = "Апрель";
                    break;
                case 5:
                    monthString = "Май";
                    break;
                case 6:
                    monthString = "Июнь";
                    break;
                case 7:
                    monthString = "Июль";
                    break;
                case 8:
                    monthString = "Август";
                    break;
                case 9:
                    monthString = "Сентябрь";
                    break;
                case 10:
                    monthString = "Октябрь";
                    break;
                case 11:
                    monthString = "Ноябрь";
                    break;
                case 12:
                    monthString = "Декабрь";
                    break;
                default:
                    monthString = null;
                    break;
            }
        }
        return monthString;
    }

    /**
     * Получить кол-во дней в месяце по дате
     *
     * @param dt - дата вх.
     * @return
     */
    public static int getCntDaysByDate(Date dt) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear(Calendar.ZONE_OFFSET);
        calendar.setTime(dt);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Получить в виде ГГГГММ месяц + - N мес.
     *
     * @param period
     */
    public static String addMonths(String period, int n) throws ParseException {
        Date dt = getDateFromPeriod(period);
        Calendar calendar = new GregorianCalendar();
        calendar.clear(Calendar.ZONE_OFFSET);
        calendar.setTime(dt);
        calendar.add(Calendar.MONTH, n);
        return getPeriodFromDate(calendar.getTime());
    }

    /**
     * Добавить или отнять N месяцев к дате
     *
     * @param dt      - базовая дата
     * @param nMonths - кол-во месяцев + -
     * @return
     */
    public static Date addMonths(Date dt, int nMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.MONTH, nMonths);
        return calendar.getTime();
    }

    /**
     * Добавить или отнять N дней к дате
     *
     * @param dt    - базовая дата
     * @param nDays - кол-во дней + -
     * @return
     */
    public static Date addDays(Date dt, int nDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_YEAR, nDays);
        return calendar.getTime();
    }

    /**
     * Добавить или отнять N секунд к дате-времени
     *
     * @param dt   - базовая дата-время
     * @param nSec - кол-во секунд + -
     * @return
     */
    public static Date addSec(Date dt, int nSec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.SECOND, nSec);
        return calendar.getTime();
    }


    /**
     * Выполнить усечение даты+время до даты
     *
     * @param date
     * @return
     */
    public static Date truncDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Выполнить усечение до секунд (отбросить миллисекунды)
     *
     * @param date
     * @return
     */
    public static Date truncDateToSeconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Добавить путь в classpath
     *
     * @param s
     * @throws Exception
     */
    public static void addPath(String s) throws Exception {
        File f = new File(s);
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{u.toURL()});
    }

    public static String getStackTraceString(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    /**
     * Преобразовать в BigDecimal, округлить до roundTo знаков, если null вернуть ZERO
     *
     * @param val     - значение для конвертации
     * @param roundTo - округлить до знаков
     * @return
     */
    public static BigDecimal getBigDecimalRound(Double val, Integer roundTo) {
        BigDecimal retVal = BigDecimal.ZERO;
        if (val != null) {
            retVal = BigDecimal.valueOf(val);
            retVal = retVal.setScale(roundTo, BigDecimal.ROUND_HALF_UP);
        }
        return retVal;
    }

    /**
     * Сравнить два BigDecimal, без учета null
     *
     * @param bdOne
     * @param bdTwo
     * @return
     */
    public static boolean isEqual(BigDecimal bdOne, BigDecimal bdTwo) {
        return Utl.nvl(bdOne, BigDecimal.ZERO)
                .compareTo(Utl.nvl(bdTwo, BigDecimal.ZERO)) == 0;
    }

    /**
     * Сравнить два Integer, без учета null
     *
     * @param bdOne - первое число
     * @param bdTwo - второе число
     */
    public static boolean isEqual(Integer bdOne, Integer bdTwo) {
        return Utl.nvl(bdOne, 0).equals(Utl.nvl(bdTwo, 0));
    }

    /**
     * Распределить одну коллекцию чисел по другой (например кредит по дебету)
     *
     * @param lst   - список который распределить (положительные значения!)
     * @param lst2  - список по которому распределить (положительные значения!)
     * @param round - число знаков округления (если с деньгами работать, то надо ставить 2)
     * @return - коллекцию корректировок (например для T_CORRECTS_PAYMENTS)
     */
    public static HashMap<Integer, Map<DistributableBigDecimal, BigDecimal>>
    distListByListIntoMap(List<? extends DistributableBigDecimal> lst,
                          List<? extends DistributableBigDecimal> lst2, int round)
            throws WrongParam {
        // найти итоги списков
        BigDecimal amntLst = lst.stream().map(DistributableBigDecimal::getBdForDist).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal amntLst2 = lst2.stream().map(DistributableBigDecimal::getBdForDist).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        if (amntLst.compareTo(BigDecimal.ZERO) == 0) {
            throw new WrongParam("ОШИБКА! Итог списка для распределения равен нулю");
        } else if (amntLst2.compareTo(BigDecimal.ZERO) == 0) {
            throw new WrongParam("ОШИБКА! Итог списка по которому распределить равен нулю");
        }
        BigDecimal limitDist;
        if (amntLst.compareTo(amntLst2) > 0) {
            // снять сумму второго списка
            limitDist = amntLst2;
        } else {
            // снять полностью всю сумму с первого списка
            limitDist = amntLst;
        }

        HashMap<Integer, Map<DistributableBigDecimal, BigDecimal>> mapRet =
                new HashMap<Integer, Map<DistributableBigDecimal, BigDecimal>>();
        // корректировки снятия с первого списка
        Map<DistributableBigDecimal, BigDecimal> mapCorrLst =
                distBigDecimalByListIntoMap(limitDist, lst, 2);
        mapRet.put(0, mapCorrLst);
        // корректировки постановки на второй список
        Map<DistributableBigDecimal, BigDecimal> mapCorrLst2 =
                distBigDecimalByListIntoMap(limitDist, lst2, 2);
        mapRet.put(1, mapCorrLst2);
        return mapRet;
    }

    /**
     * Распределить число по коллекции чисел, пропорционально вычесть из каждого элемента lst
     * внести ИЗМЕНЕНИЯ(!) в коллекцию lst, УДАЛИТЬ(!) элементы с нулями
     *
     * @param bd    - число для распределения
     * @param lst   - коллекция, содержащая числа по которым нужно распределить
     * @param round - число знаков округления (если с деньгами работать, то надо ставить 2)
     */
    public static void distBigDecimalByList(BigDecimal bd, List<? extends DistributableBigDecimal> lst, int round) {
        BigDecimal sum = lst.stream().map(DistributableBigDecimal::getBdForDist).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(BigDecimal.ZERO) != 0) {
            ListIterator<? extends DistributableBigDecimal> iter = lst.listIterator();
            while (iter.hasNext()) {
                DistributableBigDecimal elem = iter.next();
                // найти пропорцию снятия с данного элемента
                BigDecimal sumSubstract =
                        bd.multiply(elem.getBdForDist().divide(sum, 20, BigDecimal.ROUND_HALF_UP))
                                .setScale(round, BigDecimal.ROUND_HALF_UP);
                // уменьшить общую сумму;
                sum = sum.subtract(elem.getBdForDist());
                // добавить сумму в элемент
                elem.setBdForDist(elem.getBdForDist().add(sumSubstract));
                // удалить элемент, если ноль
                if (elem.getBdForDist().setScale(round, BigDecimal.ROUND_HALF_UP)
                        .equals(new BigDecimal("0.E-" + round))) {
                    iter.remove();
                }
                // вычесть сумму из числа для распределения
                bd = bd.subtract(sumSubstract);
            }
        }
    }

    /**
     * Распределить положительное число по коллекции чисел, вернуть Map элементов с корректировками
     * Распределяет корректно положительное число по массиву положительных чисел
     * БЕЗ ОКРУГЛЕНИЯ последовательно, не превышая по сумме каждый элемент
     *
     * @param bd  - число для распределения
     * @param lst - коллекция, содержащая числа по которым нужно распределить
     */
    public static Map<DistributableBigDecimal, BigDecimal>
    distBigDecimalPositiveByListIntoMap(BigDecimal bd, List<? extends DistributableBigDecimal> lst) {
        assertTrue(bd.compareTo(BigDecimal.ZERO) > 0);

        Map<DistributableBigDecimal, BigDecimal> mapResult = new HashMap<>();

        for (DistributableBigDecimal t : lst) {
            assertTrue(t.getBdForDist().compareTo(BigDecimal.ZERO) > 0);
            BigDecimal dist = BigDecimal.ZERO;
            if (t.getBdForDist().compareTo(bd) < 0) {
                dist = t.getBdForDist();
            } else {
                dist = bd;
            }
            mapResult.put(t, dist);
            bd = bd.subtract(dist);
            if (bd.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
        }
        return mapResult;
    }

    /**
     * Распределить число по коллекции чисел, вернуть Map элементов с корректировками
     * Распределяет корректно положительное или отрицательное число по массиву положит. и отрицат.чисел (можно смешанно)
     *
     * @param bd    - число для распределения
     * @param lst   - коллекция, содержащая числа по которым нужно распределить
     * @param round - число знаков округления (если с деньгами работать, то надо ставить 2)
     */
    public static Map<DistributableBigDecimal, BigDecimal>
    distBigDecimalByListIntoMap(BigDecimal bd, List<? extends DistributableBigDecimal> lst, int round) {
        BigDecimal bdFact = bd.abs();
        //log.info("CHECK");
        for (DistributableBigDecimal t : lst) {
            System.out.println(t);
        }
        // узнать, есть ли в коллекции по которой распределять, отрицательные числа
        List<? extends DistributableBigDecimal> lstNegative =
                lst.stream().filter(t -> t.getBdForDist().compareTo(BigDecimal.ZERO) < 0)
                        .collect(Collectors.toList());
        List<? extends DistributableBigDecimal> lstPositive =
                lst.stream().filter(t -> t.getBdForDist().compareTo(BigDecimal.ZERO) > 0)
                        .collect(Collectors.toList());
        if (lstNegative.size() > 0) {
            ArrayList<? extends DistributableBigDecimal> lstNegativeAbs
                    = new ArrayList<>(lstNegative);
            lstNegativeAbs.forEach(t -> t.setBdForDist(t.getBdForDist()));
            BigDecimal sumNegative = lstNegative.stream()
                    .map(DistributableBigDecimal::getBdForDist)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumAmountAbs = lst.stream()
                    .map(t -> t.getBdForDist().abs())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal proc = BigDecimal.ONE;
            if (lstPositive.size() > 0) {
                proc = sumNegative.abs().divide(sumAmountAbs, 20, RoundingMode.HALF_UP);
            }
            // фактические суммы к распределению
            BigDecimal sumFactNegative = bdFact.multiply(proc).setScale(2, RoundingMode.HALF_UP);
            Map<DistributableBigDecimal, BigDecimal> mapNegative =
                    distBigDecimalByListIntoMap(sumFactNegative, lstNegativeAbs, round);

            Map<DistributableBigDecimal, BigDecimal> mapPositive = new HashMap<>();
            if (lstPositive.size() > 0) {
                // положительная составляющая - остаток
                BigDecimal sumFactPositive = bdFact.add(sumFactNegative);
                if (sumFactPositive.compareTo(BigDecimal.ZERO) > 0) {
                    mapPositive =
                            distBigDecimalByListIntoMap(sumFactPositive, lstPositive, round);
                }
            }

            if (lstPositive.size() > 0) {
                // обратить знак в отрицательном распределении, если в распределении участвовали положительные числа
                mapNegative.entrySet().forEach(t -> t.setValue(t.getValue().negate()));
            }
            mapNegative.putAll(mapPositive);

            if (bd.signum() != bdFact.signum()) {
                // обратить знак, если распределяемое число - отрицательное
                mapNegative.entrySet().forEach(t -> t.setValue(t.getValue().negate()));
            }
            return mapNegative;
        } else {
            // нет отрицательных, распределить только по положительным
            Map<DistributableBigDecimal, BigDecimal> mapNegative =
                    distBigDecimalByListIntoMap(bdFact, lst, round);
            if (bd.signum() != bdFact.signum()) {
                // обратить знак, если распределяемое число - отрицательное
                mapNegative.entrySet().forEach(t -> t.setValue(t.getValue().negate()));
            }
            return mapNegative;
        }


    }

    /**
     * Распределить ПОЛОЖИТЕЛЬНОЕ число по коллекции ПОЛОЖИТЕЛЬНЫХ чисел, вернуть Map элементов с корректировками
     *
     * @param bd    - число для распределения
     * @param lst   - коллекция, содержащая числа по которым нужно распределить
     * @param round - число знаков округления (если с деньгами работать, то надо ставить 2)
     */
    public static Map<DistributableBigDecimal, BigDecimal>
    distBigDecimalPositiveByListIntoMap(BigDecimal bd, List<? extends DistributableBigDecimal> lst, int round) {
        Map<DistributableBigDecimal, BigDecimal> mapVol = new HashMap<>();
        BigDecimal sum = lst.stream().map(DistributableBigDecimal::getBdForDist)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        for (DistributableBigDecimal elem : lst.stream().sorted(  // отсортировать,
                Comparator.comparing(DistributableBigDecimal::getBdForDist))// чтобы небольшие числа шли первыми - так точнее
                .collect(Collectors.toList())) {
            // найти пропорцию снятия с данного элемента
/*
            log.info("elem={}", elem.getBdForDist());
            log.info("sum={}", sum);
            log.info("bd={}", bd);
            log.info("divide={}", elem.getBdForDist().divide(sum, 20, BigDecimal.ROUND_HALF_UP));
*/
            BigDecimal sumSubstract =
                    bd.multiply(elem.getBdForDist().divide(sum, 20, BigDecimal.ROUND_HALF_UP))
                            .setScale(round, BigDecimal.ROUND_HALF_UP);
//            log.info("sumSubstract={}", sumSubstract);
            // уменьшить общую сумму;
            sum = sum.subtract(elem.getBdForDist());
            // добавить сумму в элемент
            mapVol.put(elem, sumSubstract);
            // вычесть сумму из числа для распределения
            bd = bd.subtract(sumSubstract);
        }
        return mapVol;
    }

    /**
     * Получить преобразованную по шаблону строку
     *
     * @param str - исходная строка
     * @param par - параметры
     */
    public static String getStrUsingTemplate(String str, Object... par) {
        for (Object o : par) {
            String objStr = null;
            if (o instanceof BigDecimal) {
                objStr = o.toString();
            } else if (o instanceof String) {
                objStr = (String) o;
            } else if (o instanceof Integer) {
                objStr = String.valueOf(o);
            }
            assert objStr != null;
            str = str.replaceFirst("\\{}", objStr);
        }
        return str;
    }

}

