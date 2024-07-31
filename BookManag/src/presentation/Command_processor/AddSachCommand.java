package presentation.Command_processor;

import domain.SachService;
import domain.model.Sach;

public class AddSachCommand extends Command {
    
    private Sach sach;
   

    public AddSachCommand(SachService sachServiceRemote, Sach sach) {
      super(sachServiceRemote);
        this.sach = sach;
    }

    @Override
    protected void execute() {
        sachServiceRemote.addSach(sach);
    }

}
