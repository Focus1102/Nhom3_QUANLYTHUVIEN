package presentation.Command_processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.SachManagementUI;

public class SachController implements ActionListener {
    // Biến tĩnh để lưu đối tượng Singleton
    private static SachController instance = null;
    
    
    
    // Đối tượng giao diện người dùng
    private final SachManagementUI sachManagementUIRemote;
    CommandProcessor commandProcessorRemote;
    // Constructor riêng để ngăn không cho tạo đối tượng từ bên ngoài lớp
    private SachController(SachManagementUI sachManagementUIRemote, CommandProcessor commandProcessorRemote) {
        this.commandProcessorRemote = commandProcessorRemote;
        this.sachManagementUIRemote = sachManagementUIRemote;
    }
    
    // Phương thức tĩnh để lấy đối tượng Singleton
    public static synchronized SachController getInstance(SachManagementUI sachManagementUIRemote, CommandProcessor commandProcessorRemote) {
        if (instance == null) {
            instance = new SachController(sachManagementUIRemote, commandProcessorRemote);
        }
        return instance;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Command commandRemote = null;
        
        if (command.equals("Add")) {
            // Thay thế `null` bằng các tham số thực tế khi tạo `AddSachCommand`
            commandRemote = new AddSachCommand(sachManagementUIRemote.getSachService(),sachManagementUIRemote.getSach());
            commandProcessorRemote.execute(commandRemote);
        }
        
        // Thực hiện lệnh nếu `commandRemote` không phải là `null`
        if (commandRemote != null) {
            
        }
    }
    
}
