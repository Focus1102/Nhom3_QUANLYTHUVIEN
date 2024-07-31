package presentation.Command_processor;

import domain.SachService;
import domain.model.Sach;

public class EditSachCommand extends Command {
    private final Sach sach;

    public EditSachCommand(SachService sachServiceRemote, Sach sach) {
        super(sachServiceRemote);
        this.sach = sach;
    }

    @Override
    protected void execute() {
        sachServiceRemote.editSach(sach);; // Gọi phương thức cập nhật trong dịch vụ
    }
}
