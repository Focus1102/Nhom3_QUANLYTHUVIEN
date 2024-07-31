package presentation.Command_processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.model.Sach;
import presentation.SachManagementUI;

public class SachController implements ActionListener {
    // Biến tĩnh để lưu đối tượng Singleton
    private static SachController instance = null;
    
    // Đối tượng giao diện người dùng
    private final SachManagementUI sachManagementUIRemote;
    private  CommandProcessor commandProcessorRemote;
    
    // Constructor riêng để ngăn không cho tạo đối tượng từ bên ngoài lớp
    private SachController(SachManagementUI sachManagementUIRemote) {
       
        this.sachManagementUIRemote = sachManagementUIRemote;
    }
    
    // Phương thức tĩnh để lấy đối tượng Singleton
    public static synchronized SachController getInstance(SachManagementUI sachManagementUIRemote) {
        if (instance == null) {
            instance = new SachController(sachManagementUIRemote);
        }
        return instance;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Command commandRemote = null;
        commandProcessorRemote = CommandProcessor.makeCommandProcessor();
        if (command.equals("Add")) {
            commandRemote = new AddSachCommand(sachManagementUIRemote.getSachService(), sachManagementUIRemote.getSach());
            commandProcessorRemote.execute(commandRemote);
        } else if (command.equals("Remove")) {
            commandRemote = new DeleteCommand(sachManagementUIRemote.getSachService(), sachManagementUIRemote.removeSach());
            commandProcessorRemote.execute(commandRemote);
        } else if (command.equals("Cập Nhật")) {
            Sach sach = sachManagementUIRemote.getSach();
            if (sach != null) {
                commandRemote = new EditSachCommand(sachManagementUIRemote.getSachService(), sach);
                commandProcessorRemote.execute(commandRemote);
            }
        
        } else if (command.equals("Search")) {
            String keyword = sachManagementUIRemote.getSearchField().getText();
            commandRemote = new SearchSachCommand(sachManagementUIRemote.getSachService(), keyword);
            commandProcessorRemote.execute(commandRemote);
        }
        else if (command.equals("Tổng Tiền")) {
            
            commandRemote = new tTSachCommand(sachManagementUIRemote.getSachService());
            commandProcessorRemote.execute(commandRemote);
        }
    }
    
    
        // // Thực hiện lệnh nếu `commandRemote` không phải là `null`
        // if (commandRemote != null) {
        //     commandProcessorRemote.execute(commandRemote);
        // }
    }
    


