package com.ric.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/*
 * DTO для хранения записи сальдо, дебет, кредит
 * @author - Lev
 * @ver 1.00
 */
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

	public BigDecimal getIndebet() {
		return indebet;
	}

	public void setIndebet(BigDecimal indebet) {
		this.indebet = indebet;
	}

	public BigDecimal getInkredit() {
		return inkredit;
	}

	public void setInkredit(BigDecimal inkredit) {
		this.inkredit = inkredit;
	}

	public BigDecimal getOutdebet() {
		return outdebet;
	}

	public void setOutdebet(BigDecimal outdebet) {
		this.outdebet = outdebet;
	}

	public BigDecimal getOutkredit() {
		return outkredit;
	}

	public void setOutkredit(BigDecimal outkredit) {
		this.outkredit = outkredit;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getInSal() {
		return inSal;
	}

	public void setInSal(BigDecimal inSal) {
		this.inSal = inSal;
	}

	public BigDecimal getOutSal() {
		return outSal;
	}

	public void setOutSal(BigDecimal outSal) {
		this.outSal = outSal;
	}

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
			sumSaldoRecDTO.inkredit = this.inkredit;
			sumSaldoRecDTO.outkredit = this.outkredit;
			sumSaldoRecDTO.outdebet = this.outdebet;
			sumSaldoRecDTO.inSal = this.inSal;
			sumSaldoRecDTO.indebet = this.indebet;
			sumSaldoRecDTO.outSal = this.outSal;
			sumSaldoRecDTO.payment = this.payment;
			return sumSaldoRecDTO;
		}
	}


/*      Почему то проект перестал собираться 28.11.2018, убрал вот это:
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

		public BigDecimal getIndebet() {
			return indebet;
		}

		public void setIndebet(BigDecimal indebet) {
			this.indebet = indebet;
		}

		public BigDecimal getInkredit() {
			return inkredit;
		}

		public void setInkredit(BigDecimal inkredit) {
			this.inkredit = inkredit;
		}

		public BigDecimal getOutdebet() {
			return outdebet;
		}

		public void setOutdebet(BigDecimal outdebet) {
			this.outdebet = outdebet;
		}

		public BigDecimal getOutkredit() {
			return outkredit;
		}

		public void setOutkredit(BigDecimal outkredit) {
			this.outkredit = outkredit;
		}

		public BigDecimal getPayment() {
			return payment;
		}

		public void setPayment(BigDecimal payment) {
			this.payment = payment;
		}

		public BigDecimal getInSal() {
			return inSal;
		}

		public void setInSal(BigDecimal inSal) {
			this.inSal = inSal;
		}

		public BigDecimal getOutSal() {
			return outSal;
		}

		public void setOutSal(BigDecimal outSal) {
			this.outSal = outSal;
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
	}*/
}
