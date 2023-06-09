package lk.ijse.royal_care_pharmacy.entity;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;

public class Batch extends CrudDAO.SuperEntity implements SuperEntity{
    private String BatchId;
    private String BName;
    private String Description;
    private double UnitPrice;
    private int QTY;
    private String MdfExpDate;

    public Batch() {
    }

    public Batch(String batchId, String BName, String description, double unitPrice, int QTY, String mdfExpDate) {
        BatchId = batchId;
        this.BName = BName;
        Description = description;
        UnitPrice = unitPrice;
        this.QTY = QTY;
        MdfExpDate = mdfExpDate;
    }

    public String getBatchId() {
        return BatchId;
    }

    public void setBatchId(String batchId) {
        BatchId = batchId;
    }

    public String getBName() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public String getMdfExpDate() {
        return MdfExpDate;
    }

    public void setMdfExpDate(String mdfExpDate) {
        MdfExpDate = mdfExpDate;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "BatchId='" + BatchId + '\'' +
                ", BName='" + BName + '\'' +
                ", Description='" + Description + '\'' +
                ", UnitPrice=" + UnitPrice +
                ", QTY=" + QTY +
                ", MdfExpDate='" + MdfExpDate + '\'' +
                '}';
    }
}
