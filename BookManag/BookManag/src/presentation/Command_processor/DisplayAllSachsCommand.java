package presentation.Command_processor;

import java.util.List;

import domain.SachService;
import domain.model.Sach;

public class DisplayAllSachsCommand extends Command {
    
    private List<Sach> result;

    public DisplayAllSachsCommand(SachService sachServiceRemote, List<Sach> result) {
        super(sachServiceRemote);
        this.result= result;
       
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    
}
