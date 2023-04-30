package org.pantry.food.reports;

import java.util.ArrayList;
import java.util.List;

public class ReportRow {
	private List<String> columns = new ArrayList<>();
	private boolean summary = false;

	public ReportRow addColumn(String value) {
		columns.add(value);
		return this;
	}

	public List<String> getColumns() {
		return columns;
	}

	public String get0() {
		return columns.get(0);
	}

	public String get1() {
		return columns.get(1);
	}

	public String get2() {
		return columns.get(2);
	}

	public String get3() {
		return columns.get(3);
	}

	public String get4() {
		return columns.get(4);
	}

	public String get5() {
		return columns.get(5);
	}

	public String get6() {
		return columns.get(6);
	}

	public String get7() {
		return columns.get(7);
	}

	public String get8() {
		return columns.get(8);
	}

	public String get9() {
		return columns.get(9);
	}

	public String get10() {
		return columns.get(10);
	}

	public String get11() {
		return columns.get(11);
	}

	public String get12() {
		return columns.get(12);
	}

	public String get13() {
		return columns.get(13);
	}

	public String get14() {
		return columns.get(14);
	}

	public String get15() {
		return columns.get(15);
	}

	public String get16() {
		return columns.get(16);
	}

	public String get17() {
		return columns.get(17);
	}

	public String get18() {
		return columns.get(18);
	}

	public String get19() {
		return columns.get(19);
	}

	public String get20() {
		return columns.get(20);
	}

	public String get21() {
		return columns.get(21);
	}

	public String get22() {
		return columns.get(22);
	}

	public String get23() {
		return columns.get(23);
	}

	public String get24() {
		return columns.get(24);
	}

	public String get25() {
		return columns.get(25);
	}

	public String get26() {
		return columns.get(26);
	}

	public String get27() {
		return columns.get(27);
	}

	public String get28() {
		return columns.get(28);
	}

	public String get29() {
		return columns.get(29);
	}

	public String get30() {
		return columns.get(30);
	}

	public boolean isSummary() {
		return summary;
	}

	public ReportRow setSummary(boolean summary) {
		this.summary = summary;
		return this;
	}
}
