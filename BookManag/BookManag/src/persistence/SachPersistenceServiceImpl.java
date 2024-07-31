package persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; // Import List

import javax.swing.JOptionPane;

import domain.model.Sach;
import domain.model.SachGiaoKhoa;
import domain.model.SachThamKhao;

public class SachPersistenceServiceImpl implements SachPersistenceService {
    private Connect connect;

    public SachPersistenceServiceImpl() {
       // Sử dụng lớp Connect để lấy kết nối
    }

    @Override
    public void saveSach(Sach sach) {
        // Khai báo biến SQL
        String sql = null;
    
        // Khởi tạo Connection và PreparedStatement
        try (Connection connection = Connect.getConnection()) {
            if (connection == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu.");
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                return;
            }
    
            if (sach instanceof SachGiaoKhoa) {
                SachGiaoKhoa sgk = (SachGiaoKhoa) sach;
                sql = "INSERT INTO qlsachh (MaSach, NgayNhap, DonGia, SoLuong, NhaXuatBan, TinhTrang) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, sgk.getMaSach());
                    preparedStatement.setString(2, sgk.getNgayNhap());
                    preparedStatement.setDouble(3, sgk.getDonGia());
                    preparedStatement.setInt(4, sgk.getSoLuong());
                    preparedStatement.setString(5, sgk.getNhaXuatBan());
                    preparedStatement.setString(6, sgk.getTinhTrang());
                    
                    preparedStatement.executeUpdate();
                    System.out.println("Thêm sách thành công");
                    JOptionPane.showMessageDialog(null, "Thêm sách thành công");
                }
    
            } else if (sach instanceof SachThamKhao) {
                SachThamKhao stk = (SachThamKhao) sach;
                sql = "INSERT INTO qlsachh (MaSach, NgayNhap, DonGia, SoLuong, NhaXuatBan, Thue) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, stk.getMaSach());
                    preparedStatement.setString(2, stk.getNgayNhap());
                    preparedStatement.setDouble(3, stk.getDonGia());
                    preparedStatement.setInt(4, stk.getSoLuong());
                    preparedStatement.setString(5, stk.getNhaXuatBan());
                    preparedStatement.setDouble(6, stk.getThue());
                    
                    preparedStatement.executeUpdate();
                    System.out.println("Thêm sách thành công");
                    JOptionPane.showMessageDialog(null, "Thêm sách thành công");
                }
    
            } else {
                throw new IllegalArgumentException("Unknown Sach type");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu sách: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
        }
    }
    



    @Override
    public void deleteSach(int id) {
        // Cập nhật phương thức deleteSach để sử dụng cơ sở dữ liệu
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM qlsachh WHERE MaSach = " + id;
            int rowCount = statement.executeUpdate(sql);
            System.out.println("Row Count Affected: " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSach(Sach sach) {
        // Cập nhật phương thức updateSach để sử dụng cơ sở dữ liệu
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            String sql;
            if (sach instanceof SachGiaoKhoa) {
                SachGiaoKhoa sgk = (SachGiaoKhoa) sach;
                sql = String.format(
                    "UPDATE qlsachh SET NgayNhap = '%s', DonGia = %f, SoLuong = %d, NhaXuatBan = '%s', TinhTrang = '%s' " +
                    "WHERE MaSach = '%s'",
                    sgk.getNgayNhap(), sgk.getDonGia(), sgk.getSoLuong(), sgk.getNhaXuatBan(), sgk.getTinhTrang(), sgk.getMaSach()
                );
            } else if (sach instanceof SachThamKhao) {
                SachThamKhao stk = (SachThamKhao) sach;
                sql = String.format(
                    "UPDATE qlsachh SET NgayNhap = '%s', DonGia = %f, SoLuong = %d, NhaXuatBan = '%s', Thue = %f " +
                    "WHERE MaSach = '%s'",
                    stk.getNgayNhap(), stk.getDonGia(), stk.getSoLuong(), stk.getNhaXuatBan(), stk.getThue(), stk.getMaSach()
                );
            } else {
                throw new IllegalArgumentException("Unknown Sach type");
            }

            int rowCount = statement.executeUpdate(sql);
            System.out.println("Row Count Affected: " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sach getSachById(int id) {
        // Cập nhật phương thức getSachById để sử dụng cơ sở dữ liệu
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM qlsachh WHERE MaSach = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                // Xây dựng đối tượng Sach từ dữ liệu trong ResultSet
                // Thực hiện chuyển đổi và trả về đối tượng Sach tương ứng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Sach> getAllSachs() {
        // Cập nhật phương thức getAllSachs để sử dụng cơ sở dữ liệu
        List<Sach> sachs = new ArrayList<>();
        try (Connection connection = connect.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM qlsachh";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                // Xây dựng đối tượng Sach từ dữ liệu trong ResultSet
                // Thực hiện chuyển đổi và thêm vào danh sách sachs
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sachs;
    }
}
