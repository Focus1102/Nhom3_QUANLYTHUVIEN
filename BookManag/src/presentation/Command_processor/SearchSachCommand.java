package presentation.Command_processor;

import domain.SachService;


public class SearchSachCommand extends Command {
    
    private final String keyword;

    public SearchSachCommand(SachService sachService, String keyword) {
        super(sachService);
        this.keyword = keyword;
    }

    @Override
    public void execute() {
        sachServiceRemote.searchSachsByName(keyword);
        
    }
}
