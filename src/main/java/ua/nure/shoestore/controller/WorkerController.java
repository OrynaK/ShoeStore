package ua.nure.shoestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.nure.shoestore.dao.DBException;
import ua.nure.shoestore.dto.ChangeStatusDTO;
import ua.nure.shoestore.service.WorkerService;

@RestController
@CrossOrigin
public class WorkerController {
    @Autowired
    private WorkerService workerService;
    @PostMapping(value = "setWorker")
    public void setWorker(@RequestParam Long orderId, @RequestParam Long userId) {
        workerService.setWorker(orderId, userId);
    }

    @PostMapping(value = "changeStatus")
    public void changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO) throws DBException {
        workerService.changeStatus(changeStatusDTO.getOrderId(), changeStatusDTO.getUserId(), changeStatusDTO.getStatus(), changeStatusDTO.getDescription());
    }
}
