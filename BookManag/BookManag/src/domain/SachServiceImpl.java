package domain;

import java.util.ArrayList;
import java.util.List;

import domain.model.Sach;
import domain.model.SachGiaoKhoa;
import domain.model.SachThamKhao;
import persistence.SachPersistenceService;

public class SachServiceImpl implements SachService {
    private List<Sach> sachs = new ArrayList<>();
    private final SachPersistenceService sachPersistenceService;

    public SachServiceImpl(SachPersistenceService sachPersistenceService) {
        this.sachPersistenceService = sachPersistenceService;
        this.sachs = sachPersistenceService.getAllSachs();
    }

    @Override
    public void addSach(Sach sach) {
        sachPersistenceService.saveSach(sach);
    }

    @Override
    public void removeSach(int id) {
        sachs.removeIf(sach -> sach.getId() == id);
        sachPersistenceService.deleteSach(id);
    }

    @Override
    public void editSach(Sach sach) {
        for (int i = 0; i < sachs.size(); i++) {
            if (sachs.get(i).getId() == sach.getId()) {
                sachs.set(i, sach);
                sachPersistenceService.updateSach(sach);
                break;
            }
        }
    }

    @Override
    public Sach findSach(int id) {
        return sachs.stream().filter(sach -> sach.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Sach> getAllSachs() {
        return sachs;
    }

    @Override
    public double tinhTongThanhTienGiaoKhoa() {
        return sachs.stream()
                .filter(sach -> sach instanceof SachGiaoKhoa)
                .mapToDouble(Sach::thanhTien)
                .sum();
    }

    @Override
    public double tinhTongThanhTienThamKhao() {
        return sachs.stream()
                .filter(sach -> sach instanceof SachThamKhao)
                .mapToDouble(Sach::thanhTien)
                .sum();
    }

    @Override
    public double tinhTrungBinhDonGiaThamKhao() {
        return sachs.stream()
                .filter(sach -> sach instanceof SachThamKhao)
                .mapToDouble(Sach::getDonGia)
                .average()
                .orElse(0);
    }

    @Override
    public List<Sach> getSachGiaoKhoaByNhaXuatBan(String nhaXuatBan) {
        List<Sach> result = new ArrayList<>();
        for (Sach sach : sachs) {
            if (sach instanceof SachGiaoKhoa && sach.getNhaXuatBan().equalsIgnoreCase(nhaXuatBan)) {
                result.add(sach);
            }
        }
        return result;
    }

    @Override
    public List<Sach> searchSachsByName(String name) {
        List<Sach> result = new ArrayList<>();
        for (Sach sach : sachs) {
            if (sach.getMaSach().toLowerCase().contains(name.toLowerCase())) {
                result.add(sach);
            }
        }
        return result;
    }

    @Override
    public void removeSach(String maSach) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSach'");
    }

    @Override
    public Sach findSach(String maSach) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSach'");
    }

    @Override
    public void updateSach(Sach sach) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSach'");
    }

    @Override
    public List<Sach> searchSachs(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchSachs'");
    }
}
