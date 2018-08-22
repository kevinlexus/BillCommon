package com.ric.dto;

import java.math.BigDecimal;

import javax.annotation.Generated;

import lombok.Getter;
import lombok.Setter;

/*
 * DTO для хранения записи сальдо, дебет, кредит
 * @author - Lev
 * @ver 1.00
 */
@Getter @Setter
public class SumSaldoRecDTO {

	// вх.дебет
	BigDecimal indebet;
	// вх.кредит
	BigDecimal inkredit;
	// исх.дебет
	BigDecimal outdebet;
	// исх.кредит
	BigDecimal outkredit;
	// оплата
	BigDecimal payment;
	// вх.суммарное сальдо
	BigDecimal inSal;
	// исх.суммарное сальдо
	BigDecimal outSal;

	public static final class SumSaldoRecDTOBuilder {
		// вх.дебет
        BigDecimal indebet;
		// вх.кредит
        BigDecimal inkredit;
		// исх.дебет
        BigDecimal outdebet;
		// исх.кредит
        BigDecimal outkredit;
		// оплата
        BigDecimal payment;
		// вх.суммарное сальдо
        BigDecimal inSal;
		// исх.суммарное сальдо
        BigDecimal outSal;

		private SumSaldoRecDTOBuilder() {
		}

		public static SumSaldoRecDTOBuilder aSumSaldoRecDTO() {
			return new SumSaldoRecDTOBuilder();
		}

		public SumSaldoRecDTOBuilder withIndebet(BigDecimal indebet) {
			this.indebet = indebet;
			return this;
		}

		public SumSaldoRecDTOBuilder withInkredit(BigDecimal inkredit) {
			this.inkredit = inkredit;
			return this;
		}

		public SumSaldoRecDTOBuilder withOutdebet(BigDecimal outdebet) {
			this.outdebet = outdebet;
			return this;
		}

		public SumSaldoRecDTOBuilder withOutkredit(BigDecimal outkredit) {
			this.outkredit = outkredit;
			return this;
		}

		public SumSaldoRecDTOBuilder withPayment(BigDecimal payment) {
			this.payment = payment;
			return this;
		}

		public SumSaldoRecDTOBuilder withInSal(BigDecimal inSal) {
			this.inSal = inSal;
			return this;
		}

		public SumSaldoRecDTOBuilder withOutSal(BigDecimal outSal) {
			this.outSal = outSal;
			return this;
		}

		public SumSaldoRecDTO build() {
			SumSaldoRecDTO sumSaldoRecDTO = new SumSaldoRecDTO();
			sumSaldoRecDTO.setIndebet(indebet);
			sumSaldoRecDTO.setInkredit(inkredit);
			sumSaldoRecDTO.setOutdebet(outdebet);
			sumSaldoRecDTO.setOutkredit(outkredit);
			sumSaldoRecDTO.setPayment(payment);
			sumSaldoRecDTO.setInSal(inSal);
			sumSaldoRecDTO.setOutSal(outSal);
			return sumSaldoRecDTO;
		}
	}
}
