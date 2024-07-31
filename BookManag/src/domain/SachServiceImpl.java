package domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.model.Sach;
import domain.model.SachGiaoKhoa;
import domain.model.SachThamKhao;
import persistence.SachPersistenceService;

public class SachServiceImpl extends Publisher implements SachService {
    private List<Sach> sachs;
    List<Sach> result ;
    double tongTien2, tongTien ;
    DecimalFormat df = new DecimalFormat("#,###.##");
    
    private final SachPersistenceService sachPersistenceService;

    public SachServiceImpl(SachPersistenceService sachPersistenceService) {
        this.sachPersistenceService = sachPersistenceService;
        this.sachs = sachPersistenceService.getAllSachs();
    }

    @Override
    public void addSach(Sach sach) {
        sachPersistenceService.saveSach(sach);
        sachs.add(sach); // Thêm sách vào danh sách local
        thongBao();
    }

    @Override
    public void removeSach(String maSach) {
        
        sachPersistenceService.deleteSach(maSach);
        thongBao();
    }

    @Override
    public void editSach(Sach sach) {
        sachPersistenceService.updateSach(sach);
        thongBao();
    }

    @Override
    public Sach findSach(String maSach) {
        return sachs.stream()
                .filter(sach -> sach.getMaSach().equals(maSach)) // Sử dụng getMaSach() để so sánh mã sách
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Sach> getAllSachs() {
        return sachPersistenceService.getAllSachs(); // Lấy dữ liệu từ dịch vụ lưu trữ
    }

    
    

    @Override
    public double tinhTongThanhTienGiaoKhoa() {
         sachs = getAllSachs();
       tongTien = sachs.stream()
                .filter(sach -> sach instanceof SachGiaoKhoa)
                .mapToDouble(sach -> ((SachGiaoKhoa) sach).thanhTien())
                .sum();
        
        // Định dạng và hiển thị tổng tiền
        String formattedTongTien = df.format(tongTien);
        JOptionPane.showMessageDialog(null, "Tổng tiền sách giáo khoa: " + formattedTongTien);
        thongBaoTT();
        return tongTien;
    }
    
    @Override
    public double tinhTongThanhTienThamKhao() {
      sachs = getAllSachs();
        tongTien2 = sachs.stream()
                .filter(sach -> sach instanceof SachThamKhao)
                .mapToDouble(sach -> ((SachThamKhao) sach).thanhTien())
                .sum();
        
        // Định dạng và hiển thị tổng tiền
        String formattedTongTien2 = df.format(tongTien2);
        JOptionPane.showMessageDialog(null, "Tổng tiền sách tham khảo: " + formattedTongTien2);
        thongBaoTT();
        return tongTien2;
    }

    @Override
    public double getGiaGiaoKhoa() {
        return tongTien;
    }
@Override
public double getGiaThamKhao() {
    return tongTien2;
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
        sachs = sachPersistenceService.searchSachsByName(name);
        timThay();
        return sachs;
    }

   

    @Override
    public List<Sach> searchSachs(String keyword) {
        List<Sach> allSachs = getAllSachs(); 
        
        
        for (Sach sach : allSachs) { 
            if (sach.getMaSach().toLowerCase().contains(keyword.toLowerCase()) || 
                sach.getNhaXuatBan().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(sach);
            }
        }
        return result;
    }
    
@Override
public List<Sach> getTK() {
    return sachs;
}

    public void thongBao() {
        notifySubscribers();
    }
    public void thongBaoTT(){
        notifySubscriber();
    }
    public void timThay(){
        notifySubscribe();
    }

    @Override
    public double tinhTrungBinhDonGiaThamKhao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tinhTrungBinhDonGiaThamKhao'");
    }
}
