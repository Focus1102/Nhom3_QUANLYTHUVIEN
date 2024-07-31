package presentation.Command_processor;

import domain.SachService;
import domain.model.Sach;

public class EditSachCommand extends Command {
    private Sach sach;

    public EditSachCommand(SachService sachServiceRemote, Sach sach) {
        super(sachServiceRemote);
        this.sach = sach;
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}