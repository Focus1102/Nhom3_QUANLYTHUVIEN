package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.SachService;
import domain.model.Sach;
import domain.model.SachGiaoKhoa;
import domain.model.SachThamKhao;
import presentation.Command_processor.CommandProcessor;
import presentation.Command_processor.SachController;

public class SachManagementUI extends JFrame {
    private final SachService sachService;
    private final DefaultTableModel tableModel;
    private final JTable sachTable;
    private final JTextField maSachField, ngayNhapField, nhaXuatBanField, donGiaField, soLuongField, tinhTrangField;
    private final JButton addButton, removeButton, editButton, findButton, searchButton;
    private final JTextField searchField;
    SachController sachControllerRemote;

    public SachManagementUI(SachService sachService, CommandProcessor commandProcessor) {
        this.sachService = sachService;
        sachControllerRemote = SachController.getInstance(this, commandProcessor);
        
        // Initialize components
        maSachField = new JTextField(10);
        ngayNhapField = new JTextField(20);
        nhaXuatBanField = new JTextField(20);
        donGiaField = new JTextField(20);
        soLuongField = new JTextField(20);
        tinhTrangField = new JTextField(20);

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editButton = new JButton("Edit");
        findButton = new JButton("Find");
        searchButton = new JButton("Search");
        searchField = new JTextField(20);

        // Initialize table model
        String[] columnNames = {"MaSach", "NgayNhap", "NhaXuatBan", "DonGia", "SoLuong", "TinhTrang"};
        tableModel = new DefaultTableModel(columnNames, 0);
        sachTable = new JTable(tableModel);

        // Setup layout and add components
        setupLayout();

        this.setTitle("Sach Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        
        refreshSachTable();
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createInputPanel(), BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        mainPanel.add(createSearchPanel(), BorderLayout.EAST);
        mainPanel.add(new JScrollPane(sachTable), BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("MaSach:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(maSachField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("NgayNhap:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(ngayNhapField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("NhaXuatBan:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nhaXuatBanField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("DonGia:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(donGiaField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("SoLuong:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(soLuongField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("TinhTrang:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tinhTrangField, gbc);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(findButton);

        addButton.addActionListener(sachControllerRemote);
        removeButton.addActionListener(e -> removeSach());
        editButton.addActionListener(e -> editSach());
        findButton.addActionListener(e -> findSach());

        return buttonPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> searchSach());

        return searchPanel;
    }

    public Sach getSach() {
        try {
            String maSach = maSachField.getText();
            String ngayNhap = ngayNhapField.getText();
            String nhaXuatBan = nhaXuatBanField.getText();
            double donGia = Double.parseDouble(donGiaField.getText());
            int soLuong = Integer.parseInt(soLuongField.getText());
            String tinhTrang = tinhTrangField.getText();
            
            Sach sach;
            if (tinhTrang.equalsIgnoreCase("Mới") || tinhTrang.equalsIgnoreCase("Cũ")) {
                sach = new SachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
            } else {
                double thue = 0; // Default value for SachThamKhao
                sach = new SachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
            }
            return sach;
        } catch (NumberFormatException ex) {
            showMessage("Invalid number format.");
            return null;
        }
    }
    

    private void removeSach() {
        String maSach = maSachField.getText();
        sachService.removeSach(maSach);
        refreshSachTable();
    }

    private void editSach() {
        try {
            String maSach = maSachField.getText();
            Sach sach = sachService.findSach(maSach);
            if (sach != null) {
                sach.setNgayNhap(ngayNhapField.getText());
                sach.setNhaXuatBan(nhaXuatBanField.getText());
                sach.setDonGia(Double.parseDouble(donGiaField.getText()));
                sach.setSoLuong(Integer.parseInt(soLuongField.getText()));
                sach.setTinhTrang(tinhTrangField.getText());
                sachService.updateSach(sach);
                refreshSachTable();
            } else {
                showMessage("Book not found.");
            }
        } catch (NumberFormatException ex) {
            showMessage("Invalid number format.");
        }
    }

    private void findSach() {
        String maSach = maSachField.getText();
        Sach sach = sachService.findSach(maSach);
        if (sach != null) {
            ngayNhapField.setText(sach.getNgayNhap());
            nhaXuatBanField.setText(sach.getNhaXuatBan());
            donGiaField.setText(String.valueOf(sach.getDonGia()));
            soLuongField.setText(String.valueOf(sach.getSoLuong()));
            tinhTrangField.setText(sach.getTinhTrang());
        } else {
            showMessage("Book not found.");
        }
    }

    private void searchSach() {
        String keyword = searchField.getText();
        List<Sach> sachs = sachService.searchSachs(keyword);
        updateSachTable(sachs);
    }

    private void refreshSachTable() {
        updateSachTable(sachService.getAllSachs());
    }

    private void updateSachTable(List<Sach> sachs) {
        tableModel.setRowCount(0);
        for (Sach sach : sachs) {
            tableModel.addRow(new Object[]{
                sach.getMaSach(),
                sach.getNgayNhap(),
                sach.getNhaXuatBan(),
                sach.getDonGia(),
                sach.getSoLuong(),
                sach.getTinhTrang()
            });
        }
    }

    public SachService getSachService() {
        return sachService;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
