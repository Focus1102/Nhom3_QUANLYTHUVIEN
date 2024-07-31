package domain.model;

public class SachGiaoKhoa extends Sach {
    private static final long serialVersionUID = 1L;
    private String tinhTrang; // "mới" hoặc "cũ"

    public SachGiaoKhoa(String maSach, String ngayNhap, double donGia, int soLuong, String nhaXuatBan, String tinhTrang) {
        super(maSach, ngayNhap, donGia, soLuong, nhaXuatBan);
        this.tinhTrang = tinhTrang;
    }

    @Override
    public double thanhTien() {
        if (tinhTrang.equalsIgnoreCase("mới")) {
            return soLuong * donGia;
        } else {
            return soLuong * donGia * 0.5;
        }
    }

    // Getters and Setters
    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
