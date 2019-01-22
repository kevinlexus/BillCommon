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

}
