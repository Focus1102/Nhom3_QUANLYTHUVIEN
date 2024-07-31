package persistence;

import java.util.List;

import domain.model.Sach;

public interface SachPersistenceService {
    void saveSach(Sach sach);
    void deleteSach(String maSach);
    void updateSach(Sach sach);
    Sach getSachById(String maSach);
    List<Sach> getAllSachs();
    List<Sach> searchSachs(String keyword);
    List<Sach> searchSachsByName(String name)
}
