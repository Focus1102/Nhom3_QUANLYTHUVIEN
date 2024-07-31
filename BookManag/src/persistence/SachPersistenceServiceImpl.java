package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.model.Sach;
import domain.model.SachGiaoKhoa;
import domain.model.SachThamKhao;

public class SachPersistenceServiceImpl implements SachPersistenceService {

    @Override
    public void saveSach(Sach sach) {
        String sql;
        Connection connection = null;
        try {
            connection = Connect.getInstance().getConnection();
            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                return;
            }

            if (sach instanceof SachGiaoKhoa) {
                SachGiaoKhoa sgk = (SachGiaoKhoa) sach;
                sql = "INSERT INTO qlsachh (MaSach, NgayNhap, DonGia, SoLuong, NhaXuatBan, TinhTrang, Thue) VALUES (?, ?, ?, ?, ?, ?, 0)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, sgk.getMaSach());
                    preparedStatement.setString(2, sgk.getNgayNhap());
                    preparedStatement.setDouble(3, sgk.getDonGia());
                    preparedStatement.setInt(4, sgk.getSoLuong());
                    preparedStatement.setString(5, sgk.getNhaXuatBan());
                    preparedStatement.setString(6, sgk.getTinhTrang());
                    preparedStatement.executeUpdate();
                }
            } else if (sach instanceof SachThamKhao) {
                SachThamKhao stk = (SachThamKhao) sach;
                sql = "INSERT INTO qlsachh (MaSach, NgayNhap, DonGia, SoLuong, NhaXuatBan, TinhTrang, Thue) VALUES (?, ?, ?, ?, ?, '', ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, stk.getMaSach());
                    preparedStatement.setString(2, stk.getNgayNhap());
                    preparedStatement.setDouble(3, stk.getDonGia());
                    preparedStatement.setInt(4, stk.getSoLuong());
                    preparedStatement.setString(5, stk.getNhaXuatBan());
                    preparedStatement.setDouble(6, stk.getThue());
                    preparedStatement.executeUpdate();
                }
            } else {
                throw new IllegalArgumentException("Loại sách không xác định");
            }

            JOptionPane.showMessageDialog(null, "Thêm sách thành công");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu sách: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deleteSach(String maSach) {
        String sql = "DELETE FROM qlsachh WHERE MaSach = ?";
        Connection connection = null;
        try {
            connection = Connect.getInstance().getConnection();
            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                return;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maSach);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Xóa sách thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sách: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
public void updateSach(Sach sach) {
    String sql;
    Connection connection = null;
    try {
        connection = Connect.getInstance().getConnection();
        if (connection == null || connection.isClosed()) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
            return;
        }

        if (sach instanceof SachGiaoKhoa) {
            SachGiaoKhoa sgk = (SachGiaoKhoa) sach;
            sql = "UPDATE qlsachh SET NgayNhap = ?, DonGia = ?, SoLuong = ?, NhaXuatBan = ?, TinhTrang = ?, Thue = 0 WHERE MaSach = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, sgk.getNgayNhap());
                preparedStatement.setDouble(2, sgk.getDonGia());
                preparedStatement.setInt(3, sgk.getSoLuong());
                preparedStatement.setString(4, sgk.getNhaXuatBan());
                preparedStatement.setString(5, sgk.getTinhTrang());
                preparedStatement.setString(6, sgk.getMaSach());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật sách thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sách với mã: " + sgk.getMaSach());
                }
            }
        } else if (sach instanceof SachThamKhao) {
            SachThamKhao stk = (SachThamKhao) sach;
            sql = "UPDATE qlsachh SET NgayNhap = ?, DonGia = ?, SoLuong = ?, NhaXuatBan = ?, TinhTrang = '', Thue = ? WHERE MaSach = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, stk.getNgayNhap());
                preparedStatement.setDouble(2, stk.getDonGia());
                preparedStatement.setInt(3, stk.getSoLuong());
                preparedStatement.setString(4, stk.getNhaXuatBan());
                preparedStatement.setDouble(5, stk.getThue());
                preparedStatement.setString(6, stk.getMaSach());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật sách thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sách với mã: " + stk.getMaSach());
                }
            }
        } else {
            throw new IllegalArgumentException("Loại sách không xác định");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật sách: " + e.getMessage());
    } finally {
        closeConnection(connection);
    }
}
@Override
public List<Sach> searchSachs(String keyword) {
    List<Sach> allSachs = getAllSachs(); 
    List<Sach> result = new ArrayList<>();
    
    for (Sach sach : allSachs) { 
        if (sach.getMaSach().toLowerCase().contains(keyword.toLowerCase()) || 
            sach.getNhaXuatBan().toLowerCase().contains(keyword.toLowerCase())) {
            result.add(sach);
        }
    }
    return result;
}

@Override
public List<Sach> searchSachsByName(String name) {
    List<Sach> sachs = new ArrayList<>();
    String sql = "SELECT * FROM qlsachh WHERE MaSach LIKE ?"; // Hoặc thay bằng trường khác nếu cần
    Connection connection = null;
    
    try {
        connection = Connect.getInstance().getConnection();
        if (connection == null || connection.isClosed()) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
            return sachs;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%"); // Tìm kiếm với từ khóa
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String maSach = resultSet.getString("MaSach");
                String ngayNhap = resultSet.getString("NgayNhap");
                double donGia = resultSet.getDouble("DonGia");
                int soLuong = resultSet.getInt("SoLuong");
                String nhaXuatBan = resultSet.getString("NhaXuatBan");
                String tinhTrang = resultSet.getString("TinhTrang");
                double thue = resultSet.getDouble("Thue");

                Sach sach;
                if (thue > 0) {
                    sach = new SachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
                } else {
                    sach = new SachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
                }
                sachs.add(sach);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm sách: " + e.getMessage());
    } finally {
        closeConnection(connection);
    }
    
    return sachs;
}


    @Override
    public Sach getSachById(String maSach) {
        String sql = "SELECT * FROM qlsachh WHERE MaSach = ?";
        Connection connection = null;
        try {
            connection = Connect.getInstance().getConnection();
            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                return null;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maSach);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String ngayNhap = resultSet.getString("NgayNhap");
                    double donGia = resultSet.getDouble("DonGia");
                    int soLuong = resultSet.getInt("SoLuong");
                    String nhaXuatBan = resultSet.getString("NhaXuatBan");
                    String tinhTrang = resultSet.getString("TinhTrang");
                    double thue = resultSet.getDouble("Thue");

                    if (thue > 0) {
                        return new SachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
                    } else {
                        return new SachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sách với mã: " + maSach);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy sách: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
public List<Sach> getAllSachs() {
    List<Sach> sachs = new ArrayList<>();
    String sql = "SELECT * FROM qlsachh";
    Connection connection = null;
    try {
        connection = Connect.getInstance().getConnection();
        if (connection == null || connection.isClosed()) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
            return sachs;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String maSach = resultSet.getString("MaSach");
                String ngayNhap = resultSet.getString("NgayNhap");
                double donGia = resultSet.getDouble("DonGia");
                int soLuong = resultSet.getInt("SoLuong");
                String nhaXuatBan = resultSet.getString("NhaXuatBan");
                String tinhTrang = resultSet.getString("TinhTrang");
                double thue = resultSet.getDouble("Thue");

                Sach sach;
                if (thue > 0) {
                    sach = new SachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
                } else {
                    sach = new SachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
                }
                sachs.add(sach);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách sách: " + e.getMessage());
    } finally {
        closeConnection(connection);
    }
    return sachs;
}

    // Phương thức để đóng kết nối cơ sở dữ liệu
    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
