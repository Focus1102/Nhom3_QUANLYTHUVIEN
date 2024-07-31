package domain.model;

public class SachThamKhao extends Sach {
    private static final long serialVersionUID = 1L;
    private double thue;

    public SachThamKhao(String maSach, String ngayNhap, double donGia, int soLuong, String nhaXuatBan, double thue) {
        super(maSach, ngayNhap, donGia, soLuong, nhaXuatBan);
        this.thue = thue;
    }

    @Override
    public double thanhTien() {
        return soLuong * donGia + thue;
    }

    // Getters and Setters
    public double getThue() {
        return thue;
    }

    public void setThue(double thue) {
        this.thue = thue;
    }

    @Override
    public void setTinhTrang(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTinhTrang'");
    }
}
