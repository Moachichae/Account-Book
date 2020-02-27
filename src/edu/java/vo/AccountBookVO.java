package edu.java.vo;

public class AccountBookVO {

	private String id;
	private String date_;
	private String incomeType;
	private String assetType;
	private String detail;
	private int money;
	private int cid;

	public AccountBookVO() {
	}

	public AccountBookVO(String id, String date_, String incomeType, String assetType, String detail, int money, int cid) {
		this.id = id;
		this.date_ = date_;
		this.incomeType = incomeType;
		this.assetType = assetType;
		this.detail = detail;
		this.money = money;
		this.cid = cid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate_() {
		return date_;
	}

	public void setDate_(String date_) {
		this.date_ = date_;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getcid() {
		return cid;
	}

	public void setcid(int cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		String str = " id : " + id + "\n date_ : " + date_ + "\n incomType : " + incomeType + "\n assetType: "
				+ assetType + "\n detail : " + detail + "\n money : " + money + "\n cid : " + cid;
		return str;
	}

} // end Account+bookVO
