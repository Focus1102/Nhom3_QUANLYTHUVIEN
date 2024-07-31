import domain.SachService;
import domain.SachServiceImpl;
import persistence.SachPersistenceService;
import persistence.SachPersistenceServiceImpl;
import presentation.SachManagementUI;
import presentation.Command_processor.CommandProcessor;

public class App {
    public static void main(String[] args) {
        
        // Khởi tạo dịch vụ lưu trữ sách với đường dẫn tệp
        SachPersistenceService sachPersistenceService = new SachPersistenceServiceImpl();

        // Khởi tạo SachServiceImpl với dịch vụ lưu trữ sách
        SachService sachService = new SachServiceImpl(sachPersistenceService);
        CommandProcessor commandProcessor = CommandProcessor.makeCommandProcessor();
      
        // Khởi tạo và hiển thị giao diện người dùng
       SachManagementUI sachManagementUIRemote = new SachManagementUI(sachService, commandProcessor);
    }
}
