import com.ric.cmn.DistributableBigDecimal;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@Slf4j
public class TestUtl {

    /**
     * проверка распределения Bigdecimal числа по коллекции Bigdecimal чисел
     */
    @Test
    public void isWorkUtlDistBigDecimalByList() throws Exception {

        log.info("-----------------Begin ");

        // любой класс, реализующий интерфейс "распределяемый BD"
        @Getter @Setter
        class Check implements DistributableBigDecimal {
            int someIdx;
            String someStr;
            BigDecimal someValue;

            public Check(int someIdx, String someStr, String strBd) {
                this.someIdx = someIdx;
                this.someStr = someStr;
                this.someValue = new BigDecimal(strBd);
            }

            public BigDecimal getBdForDist() {
                return someValue;
            }

            public void setBdForDist(BigDecimal bd) {
                this.someValue = bd;
            }
        }

        List<Check> lst = new ArrayList<>();

        lst.add(new Check(1, "bla1", "153.512578"));
        lst.add(new Check(2, "bla2", "25.59344"));
        lst.add(new Check(3, "bla3", "5.584"));
        lst.add(new Check(4, "bla4", "1.565"));
        lst.add(new Check(5, "bla5", "0.576"));
        lst.add(new Check(6, "bla6", "11.5677"));
        lst.add(new Check(7, "bla7", "106.458"));
        lst.add(new Check(8, "bla8", "7.3500001"));
        lst.add(new Check(9, "bla9", "8.25223"));
        lst.add(new Check(10, "bla10", "12.334"));

        BigDecimal amnt = lst.stream().map(t -> t.getBdForDist()).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("amnt={}", amnt);

        //List<DistributableBigDecimal> ddd = lst;
        // распределить
        BigDecimal val = new BigDecimal("-332.7929481");
        Utl.distBigDecimalByList(val, lst, 7);

        log.info("распределение:");
        for (DistributableBigDecimal t : lst) {
            log.info("elem = {}", new DecimalFormat("#0.#############").format(t.getBdForDist()));
        }
        log.info("");
        BigDecimal amntDist = lst.stream().map(t -> t.getBdForDist()).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого = {}", amntDist);

        assertTrue(amnt.add(val).equals(new BigDecimal("0.E-7")));
        log.info("-----------------End");
    }

    /**
     * проверка распределения Bigdecimal числа по коллекции Bigdecimal чисел
     * @throws Exception
     */
    @Test
    public void isWorkUtlDistBigDecimalByList2() throws Exception {

        List<KartVol> lst = new ArrayList<>(10);

        log.info("1.0 Распределить положительное число по коллекции положительных чисел");
        // 1.0 Распределить положительное число по коллекции положительных чисел

        lst.add(new KartVol("0002", new BigDecimal("0.02")));
        lst.add(new KartVol("0003", new BigDecimal("3.17")));
        lst.add(new KartVol("0004", new BigDecimal("2775.25")));
        lst.add(new KartVol("0005", new BigDecimal("77.37")));
        lst.add(new KartVol("0006", new BigDecimal("9.57")));
        lst.add(new KartVol("0007", new BigDecimal("0.05")));
        lst.add(new KartVol("0008", new BigDecimal("0.05")));
        lst.add(new KartVol("0009", new BigDecimal("0.15")));
        lst.add(new KartVol("0010", new BigDecimal("0.01")));

        BigDecimal amnt = lst.stream().map(KartVol::getBdForDist).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("amnt area={}", amnt);

        // распределить
        BigDecimal val = new BigDecimal("936.86");
        Map<DistributableBigDecimal, BigDecimal> map = Utl.distPositiveBigDecimalByListIntoMap(val, lst, 2);

        log.info("распределение:");
        for (Map.Entry<DistributableBigDecimal, BigDecimal> t : map.entrySet()) {
            KartVol kartVol = (KartVol) t.getKey();
            log.info("elem = {}, vol={}", kartVol.getLsk(),
                    new DecimalFormat("#0.#############").format(t.getValue()));
        }

        log.info("");
        BigDecimal amntDist = map.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого = {}", amntDist);
        assertTrue(amntDist.compareTo(val) ==0);


        log.info("2.0 Распределить положительное или отрицательное число по коллекции положительных и отрицательных чисел");
        // 2.0 Распределить положительное число по коллекции положительных и отрицательных чисел
        lst.clear();
        lst.add(new KartVol("0001", new BigDecimal("0.02")));
        lst.add(new KartVol("0002", new BigDecimal("-0.02")));
        lst.add(new KartVol("0004", new BigDecimal("2775.25")));
        lst.add(new KartVol("0005", new BigDecimal("77.37")));
        lst.add(new KartVol("0006", new BigDecimal("9.57")));
        lst.add(new KartVol("0007", new BigDecimal("-0.05")));
        lst.add(new KartVol("0008", new BigDecimal("0.05")));
        lst.add(new KartVol("0009", new BigDecimal("-1000.15")));
        lst.add(new KartVol("0010", new BigDecimal("0.01")));

        amnt = lst.stream().map(KartVol::getBdForDist).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("amnt area={}", amnt);

        // распределить
        val = new BigDecimal("936.86");
        map = Utl.distBigDecimalByListIntoMap(val, lst, 2);

        log.info("распределение:");
        for (Map.Entry<DistributableBigDecimal, BigDecimal> t : map.entrySet()) {
            KartVol kartVol = (KartVol) t.getKey();
            log.info("elem = {}, vol={}", kartVol.getLsk(),
                    new DecimalFormat("#0.#############").format(t.getValue()));
        }

        log.info("");
        amntDist = map.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого = {}", amntDist);
        assertTrue(amntDist.compareTo(val) ==0);
    }

    /**
     * Проверка распределения списка чисел по другому списку,
     * например кредитового сальдо по дебетовому
     */
    @Test
    public void testUtlDistListByList() throws Exception {
        List<KartVol> lst = new ArrayList<>(10);

        lst.add(new KartVol("0001", new BigDecimal("5.11")));
        lst.add(new KartVol("0002", new BigDecimal("55.21")));
        lst.add(new KartVol("0003", new BigDecimal("99.27")));
        lst.add(new KartVol("0004", new BigDecimal("5.05")));
        lst.add(new KartVol("0005", new BigDecimal("575.13")));
        lst.add(new KartVol("0006", new BigDecimal("7.27")));
        BigDecimal amnt1 = lst.stream().map(KartVol::getBdForDist)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого 1 список:{}", amnt1) ;

        List<KartVol> lst2 = new ArrayList<>(10);

        lst2.add(new KartVol("0007", new BigDecimal("15.23")));
        lst2.add(new KartVol("0008", new BigDecimal("58.29")));
        lst2.add(new KartVol("0009", new BigDecimal("55.42")));
        lst2.add(new KartVol("0010", new BigDecimal("1.28")));
       // lst2.add(new KartVol("0011", new BigDecimal("1001.28")));
       // lst2.add(new KartVol("0012", new BigDecimal("2051.28")));
        BigDecimal amnt2 = lst2.stream().map(KartVol::getBdForDist)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого 2 список:{}", amnt2) ;

        HashMap<Integer, Map<DistributableBigDecimal, BigDecimal>> map =
                Utl.distListByListIntoMap(lst, lst2, 2);
        log.info("снятие с первого списка:");
        map.get(0).forEach((a,b)-> {
            KartVol d = (KartVol) a;
            log.info("lsk={}, summa={}", d.getLsk(), b);
        });
        amnt1 = map.get(0).values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого:{}", amnt1) ;
        log.info("постановка на второй список:");
        map.get(1).forEach((a,b)-> {
            KartVol d = (KartVol) a;
            log.info("lsk={}, summa={}", d.getLsk(), b);
        });
        amnt2 = map.get(0).values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого:{}", amnt2) ;

        assertTrue(amnt1.compareTo(amnt2) == 0);
    }

    @Getter @Setter
    class KartVol implements DistributableBigDecimal {
        // лиц.счет
        private String lsk;
        // площадь
        private BigDecimal area;

        public KartVol(String lsk, BigDecimal area) {
            this.lsk = lsk;
            this.area = area;
        }

        @Override
        public BigDecimal getBdForDist() {
            return area;
        }

        @Override
        public void setBdForDist(BigDecimal bd) {
        }

    }

}
