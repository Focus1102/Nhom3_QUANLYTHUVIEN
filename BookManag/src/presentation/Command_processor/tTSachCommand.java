package presentation.Command_processor;

import domain.SachService;

public class tTSachCommand extends Command {

    public tTSachCommand(SachService sachServiceRemote) {
        super(sachServiceRemote);
        
    }

    @Override
    protected void execute() {
       sachServiceRemote.tinhTongThanhTienGiaoKhoa();
       sachServiceRemote.tinhTongThanhTienThamKhao();
    }

}
