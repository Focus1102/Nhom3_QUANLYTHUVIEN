package presentation.Command_processor;

import domain.SachService;

public class RemoveSachCommand extends Command {
    private int sachId;

    public RemoveSachCommand(SachService sachServiceRemote, int sachId) {
        super(sachServiceRemote);
        this.sachId = sachId;
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}