import domain.SachService;
import domain.SachServiceImpl;
import persistence.SachPersistenceService;
import persistence.SachPersistenceServiceImpl;
import presentation.SachManagementUI;

public class App {
    public static void main(String[] args) {
        
        SachPersistenceService sachPersistenceService = new SachPersistenceServiceImpl();

        SachService sachService = new SachServiceImpl(sachPersistenceService);
      
        // Khởi tạo và hiển thị giao diện người dùng
       SachManagementUI sachManagementUIRemote = new SachManagementUI(sachService);
    }
}
