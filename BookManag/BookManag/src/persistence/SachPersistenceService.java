package persistence;

import java.util.List;
import domain.model.Sach;

public interface SachPersistenceService {
    void saveSach(Sach sach);
    void deleteSach(int id);
    void updateSach(Sach sach);
    Sach getSachById(int id);
    List<Sach> getAllSachs();
}
