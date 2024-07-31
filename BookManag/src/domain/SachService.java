package domain;

import java.util.List;

import domain.model.Sach;

public interface SachService {
    
    void addSach(Sach sach);
    void removeSach(String maSach);
    void editSach(Sach sach);
    Sach findSach(String maSach);
    List<Sach> getAllSachs();
    List<Sach> getTK();
    double tinhTongThanhTienGiaoKhoa();
    double tinhTongThanhTienThamKhao();
    double tinhTrungBinhDonGiaThamKhao();
    List<Sach> getSachGiaoKhoaByNhaXuatBan(String nhaXuatBan);
    List<Sach> searchSachsByName(String name);
    double getGiaThamKhao();
    double getGiaGiaoKhoa();

    List<Sach> searchSachs(String keyword);
}
