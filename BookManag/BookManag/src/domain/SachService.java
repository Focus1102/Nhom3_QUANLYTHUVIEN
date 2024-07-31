package domain;

import java.util.List;

import domain.model.Sach;

public interface SachService {
    void addSach(Sach sach);
    void removeSach(int id);
    void editSach(Sach sach);
    Sach findSach(int id);
    List<Sach> getAllSachs();
    double tinhTongThanhTienGiaoKhoa();
    double tinhTongThanhTienThamKhao();
    double tinhTrungBinhDonGiaThamKhao();
    List<Sach> getSachGiaoKhoaByNhaXuatBan(String nhaXuatBan);
    List<Sach> searchSachsByName(String name);
    void removeSach(String maSach);
    Sach findSach(String maSach);
    void updateSach(Sach sach);
    List<Sach> searchSachs(String keyword);
}
