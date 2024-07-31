package presentation.Command_processor;


import domain.SachService;

public class DeleteCommand extends Command {
    private int id;
    public DeleteCommand(SachService sachServiceRemote,int id){
        super(sachServiceRemote);
        this.id = id;
       
    }
    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

  
}
