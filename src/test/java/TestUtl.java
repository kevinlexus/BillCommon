import com.ric.cmn.DistributableBigDecimal;
import com.ric.cmn.Utl;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@Slf4j
public class TestUtl {

    /**
     * проверка распределения Bigdecimal числа по коллекции Bigdecimal чисел
     * @throws Exception
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

        List<KartVol> lst = new ArrayList<>(10);

        lst.add(new KartVol("0001", new BigDecimal("5.23")));
        lst.add(new KartVol("0002", new BigDecimal("55.23")));
        lst.add(new KartVol("0003", new BigDecimal("15.23")));
        lst.add(new KartVol("0004", new BigDecimal("55.23")));
        lst.add(new KartVol("0005", new BigDecimal("55.23")));
        lst.add(new KartVol("0006", new BigDecimal("5.23")));
        lst.add(new KartVol("0007", new BigDecimal("55.23")));
        lst.add(new KartVol("0008", new BigDecimal("55.23")));
        lst.add(new KartVol("0009", new BigDecimal("5.23")));
        lst.add(new KartVol("0010", new BigDecimal("1.23")));

        BigDecimal amnt = lst.stream().map(t -> t.getBdForDist()).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("amnt area={}", amnt);

        // распределить
        BigDecimal val = new BigDecimal("332.7929481");
        Map<DistributableBigDecimal, BigDecimal> map = Utl.distBigDecimalByListIntoMap(val, lst, 7);

        log.info("распределение:");
        for (Map.Entry<DistributableBigDecimal, BigDecimal> t : map.entrySet()) {
            KartVol kartVol = (KartVol) t.getKey();
            log.info("elem = {}, vol={}", kartVol.getLsk(),
                    new DecimalFormat("#0.#############").format(t.getValue()));
        }

        log.info("");
        BigDecimal amntDist = map.entrySet().stream().map(t->t.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("итого = {}", amntDist);

    }

}
