package dto;

public class SachThamKhaoDTO extends SachDTO {
    private double thue;

    public SachThamKhaoDTO(String maSach, String ngayNhap, double donGia, int soLuong, String nhaXuatBan, double thue) {
        super(maSach, ngayNhap, donGia, soLuong, nhaXuatBan);
        this.thue = thue;
    }

    // Getters and Setters
    public double getThue() { return thue; }
    public void setThue(double thue) { this.thue = thue; }
}
