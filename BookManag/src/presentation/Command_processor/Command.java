package presentation.Command_processor;

import domain.SachService;

public abstract class Command {

    protected static SachService sachServiceRemote;

    public Command(SachService sachServiceRemote) {
        this.sachServiceRemote = sachServiceRemote;
    }

    protected abstract void execute();

}





