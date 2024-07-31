package presentation.Command_processor;

import java.util.List;

import domain.SachService;
import domain.model.Sach;

public class SearchSachCommand extends Command {
    private String name;
    private List<Sach> result;

    public SearchSachCommand(SachService sachServiceRemote, String name,List<Sach> result) {
        super(sachServiceRemote);
        this.result=result;
        this.name = name;
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}

