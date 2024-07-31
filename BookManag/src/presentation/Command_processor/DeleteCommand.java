package presentation.Command_processor;


import domain.SachService;

public class DeleteCommand extends Command {
    private String maSach;
    public DeleteCommand(SachService sachServiceRemote,String maSach){
        super(sachServiceRemote);
        this.maSach = maSach;
       
    }
    @Override
    protected void execute() {
       sachServiceRemote.removeSach(maSach);
    }

  
}
