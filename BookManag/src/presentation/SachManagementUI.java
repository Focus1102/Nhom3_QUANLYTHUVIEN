package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.SachService;
import domain.SachServiceImpl;
import domain.Subscriber;
import domain.model.Sach;
import domain.model.SachGiaoKhoa;
import domain.model.SachThamKhao;
import presentation.Command_processor.SachController;

public class SachManagementUI extends JFrame implements Subscriber {
    private final SachService sachService;
    private final DefaultTableModel tableModel;
    private final JTable sachTable;
    private final JTextField maSachField, ngayNhapField, nhaXuatBanField, donGiaField, soLuongField, tinhTrangField, thueField;
    private final JTextField tongTienGiaoKhoaField; // JTextField cho tổng tiền sách giáo khoa
    private final JTextField tongTienThamKhaoField; // JTextField cho tổng tiền sách tham khảo
    private final JButton addButton, removeButton, editButton, tongTienButton, searchButton;
    private final JTextField searchField;
    DecimalFormat df = new DecimalFormat("#,###.##");
    private final JComboBox<String> typeComboBox; // JComboBox để chọn thể loại sách
    private SachController sachControllerRemote;


    public SachManagementUI(SachService sachService) {
        this.sachService = sachService;
        ((SachServiceImpl) sachService).subscribe(this);
        sachControllerRemote = SachController.getInstance(this);
        
        // Initialize components
        maSachField = new JTextField(10);
        ngayNhapField = new JTextField(20);
        nhaXuatBanField = new JTextField(20);
        donGiaField = new JTextField(20);
        soLuongField = new JTextField(20);
        tinhTrangField = new JTextField(20);
        thueField = new JTextField(20);

        // JTextFields for total amounts
        tongTienGiaoKhoaField = new JTextField(20);
        tongTienGiaoKhoaField.setEditable(false); // Không cho chỉnh sửa
        tongTienThamKhaoField = new JTextField(20);
        tongTienThamKhaoField.setEditable(false); // Không cho chỉnh sửa

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editButton = new JButton("Edit");
        tongTienButton = new JButton("Tổng Tiền");
        
        searchButton = new JButton("Search");
        searchField = new JTextField(20);
        
        // JComboBox để chọn thể loại sách
        typeComboBox = new JComboBox<>(new String[]{"Sách Giáo Khoa", "Sách Tham Khảo"});
        typeComboBox.addActionListener(e -> toggleFields());

        // Initialize table model
        String[] columnNames = {"Mã Sách", "Ngày Nhập", "Nhà Xuất Bản", "Đơn Giá", "Số Lượng", "Tình Trạng", "Thuế"};
        tableModel = new DefaultTableModel(columnNames, 0);
        sachTable = new JTable(tableModel);
        sachTable.setDefaultEditor(Object.class, null); // Chặn chỉnh sửa bảng

        // Setup layout and add components
        setupLayout();

        this.setTitle("Hệ Thống Quản Lý Sách");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setVisible(true);
        
        refreshSachTable();

        // Thêm lắng nghe sự kiện cho nút Edit
        editButton.addActionListener(e -> editSach());
    }

    private void toggleFields() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        boolean isGiaoKhoa = "Sách Giáo Khoa".equals(selectedType);
        tinhTrangField.setEnabled(isGiaoKhoa);
        thueField.setEnabled(!isGiaoKhoa);
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createInputPanel(), BorderLayout.CENTER);
        mainPanel.add(createSearchPanel(), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(sachTable), BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Thể loại:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Mã Sách:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(maSachField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Ngày Nhập:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(ngayNhapField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Nhà Xuất Bản:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nhaXuatBanField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Đơn Giá:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(donGiaField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Số Lượng:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(soLuongField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Tình Trạng:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tinhTrangField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Thuế:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(thueField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Tổng tiền sách giáo khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tongTienGiaoKhoaField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Tổng tiền sách tham khảo:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tongTienThamKhaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonPanel = createButtonPanel();
        inputPanel.add(buttonPanel, gbc);

        // Disable fields based on the selected type
        toggleFields();

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(tongTienButton);
        
        // Chỉ tính tổng tiền khi nhấn nút "Tổng Tiền"
        tongTienButton.addActionListener(sachControllerRemote);
        addButton.addActionListener(sachControllerRemote);
        removeButton.addActionListener(sachControllerRemote);
        
        return buttonPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Tìm Kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(sachControllerRemote);

        return searchPanel;
    }

private void updateTongTien() {
    double tongTienGiaoKhoa = sachService.getGiaGiaoKhoa();
    double tongTienThamKhao = sachService.getGiaThamKhao();
    
     // Định dạng số với dấu phẩy và 2 chữ số thập phân
    tongTienGiaoKhoaField.setText(df.format(tongTienGiaoKhoa));
    tongTienThamKhaoField.setText(df.format(tongTienThamKhao));
}

    public Sach getSach() {
        try {
            String maSach = maSachField.getText();
            String ngayNhap = ngayNhapField.getText();
            String nhaXuatBan = nhaXuatBanField.getText();
            double donGia = Double.parseDouble(donGiaField.getText());
            int soLuong = Integer.parseInt(soLuongField.getText());
            String tinhTrang = tinhTrangField.getText();
            double thue = 0; // Khởi tạo thuế mặc định là 0
    
            Sach sach;
            if (typeComboBox.getSelectedItem().equals("Sách Giáo Khoa")) {
                sach = new SachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
            } else {
                thue = Double.parseDouble(thueField.getText()); // Chỉ lấy thuế khi là sách tham khảo
                sach = new SachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
            }
            return sach;
        } catch (NumberFormatException ex) {
            showMessage("Định dạng số không hợp lệ.");
            return null;
        } catch (Exception e) {
            showMessage("Lỗi: " + e.getMessage());
            return null;
        }
    }

    public String removeSach() {
        String ma;
        int selectedRow = sachTable.getSelectedRow();
        if (selectedRow >= 0) { // Kiểm tra nếu có dòng nào được chọn
            String maSach = (String) tableModel.getValueAt(selectedRow, 0); // Lấy mã sách từ cột đầu tiên
            ma = maSach; 
        } else {
            showMessage("Chưa chọn sách nào.");
            ma = null;
        }
        return ma;
    }

    private Sach editSach() {
        Sach sach;
        int selectedRow = sachTable.getSelectedRow();
        
        if (selectedRow >= 0) { // Kiểm tra nếu có dòng nào được chọn
            if (editButton.getText().equals("Edit")) {
                // Điền thông tin vào các JTextField
                maSachField.setText((String) tableModel.getValueAt(selectedRow, 0));
                ngayNhapField.setText((String) tableModel.getValueAt(selectedRow, 1));
                nhaXuatBanField.setText((String) tableModel.getValueAt(selectedRow, 2));
                donGiaField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
                soLuongField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
                
                if (tableModel.getValueAt(selectedRow, 5) != null) {
                    tinhTrangField.setText((String) tableModel.getValueAt(selectedRow, 5));
                } else {
                    tinhTrangField.setText("");
                }
                
                if (tableModel.getValueAt(selectedRow, 6) != null) {
                    thueField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 6)));
                } else {
                    thueField.setText("");
                }
                
                editButton.setText("Cập Nhật");
                editButton.addActionListener(sachControllerRemote); // Đổi tên nút thành "Cập Nhật"
                sach = null;
            } else { // Khi nhấn "Cập Nhật"
                sach = getSach(); // Lấy thông tin từ các JTextField
                
                if (sach != null) {
                    // Cập nhật thông tin sách
                    tableModel.setValueAt(sach.getMaSach(), selectedRow, 0);
                    tableModel.setValueAt(sach.getNgayNhap(), selectedRow, 1);
                    tableModel.setValueAt(sach.getNhaXuatBan(), selectedRow, 2);
                    tableModel.setValueAt(sach.getDonGia(), selectedRow, 3);
                    tableModel.setValueAt(sach.getSoLuong(), selectedRow, 4);
                    if (sach instanceof SachGiaoKhoa) {
                        tableModel.setValueAt(((SachGiaoKhoa) sach).getTinhTrang(), selectedRow, 5);
                        tableModel.setValueAt(null, selectedRow, 6);
                    } else if (sach instanceof SachThamKhao) {
                        tableModel.setValueAt(null, selectedRow, 5);
                        tableModel.setValueAt(((SachThamKhao) sach).getThue(), selectedRow, 6);
                    }
                    
                    // Đổi lại tên nút thành "Edit" sau khi cập nhật
                    editButton.setText("Edit");
                }
            }
        } else {
            sach = null;
        }
        return sach;
    }

    private void findSach() {
        String maSach = maSachField.getText();
        Sach sach = sachService.findSach(maSach);
        if (sach != null) {
            ngayNhapField.setText(sach.getNgayNhap());
            nhaXuatBanField.setText(sach.getNhaXuatBan());
            donGiaField.setText(String.valueOf(sach.getDonGia()));
            soLuongField.setText(String.valueOf(sach.getSoLuong()));
            if (sach instanceof SachThamKhao) {
                thueField.setText(String.valueOf(((SachThamKhao) sach).getThue()));
                tinhTrangField.setText("");
            } else {
                tinhTrangField.setText(sach.getTinhTrang());
                thueField.setText("");
            }
        } else {
            showMessage("Không tìm thấy sách.");
        }
    }

    public String searchSach() {
        String keyword = searchField.getText();
        return keyword;
    }

    public void refreshSachTable() {
        updateSachTable(sachService.getAllSachs()); // Cập nhật bảng với danh sách sách
        // Không tự động cập nhật tổng tiền
    }

    private void updateSachTable(List<Sach> sachs) {
        tableModel.setRowCount(0); // Xóa tất cả các dòng hiện tại
        for (Sach sach : sachs) {
            Object[] row = new Object[]{
                sach.getMaSach(),
                sach.getNgayNhap(),
                sach.getNhaXuatBan(),
                sach.getDonGia(),
                sach.getSoLuong(),
                sach instanceof SachGiaoKhoa ? ((SachGiaoKhoa) sach).getTinhTrang() : "",
                sach instanceof SachThamKhao ? ((SachThamKhao) sach).getThue() : ""
            };
            tableModel.addRow(row);
        }
    }

    public void setSearch(List<Sach> sach) {
        updateSachTable(sach);
    }

    public SachService getSachService() {
        return sachService;
    }
    
    public JTextField getSearchField() {
        return searchField;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void update() {
        refreshSachTable();
    }

    @Override
    public void updates() {
        updateTongTien();
    }
    @Override
    public void updateS() {
        updateSachTable(sachService.getTK());
    }
}
