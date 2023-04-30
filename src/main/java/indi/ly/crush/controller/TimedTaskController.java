package indi.ly.crush.controller;

import indi.ly.crush.model.TimedTask;
import indi.ly.crush.service.ITimedTaskService;
import indi.ly.crush.util.MapUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <h2>定时任务控制器</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/")
public class TimedTaskController {
	final ITimedTaskService timedTaskServiceImpl;
	
	public TimedTaskController(ITimedTaskService timedTaskServiceImpl) {
		this.timedTaskServiceImpl = timedTaskServiceImpl;
	}
	
	@PostMapping(value = "v1/task")
	public Map<String, Object> goToAddTimedTask(@RequestBody TimedTask timedTask) {
		this.timedTaskServiceImpl.addTimedTask(timedTask);
		return MapUtil.ok(200,"Timed task added successfully");
	}
	
	@GetMapping(value = "v1/tasks")
	public Map<String, Object> goToGetAllTimedTasks() {
		List<TimedTask> tasks = this.timedTaskServiceImpl.getAllTimedTasks();
		return MapUtil.ok(200,"", tasks);
	}
	
	@PutMapping(value = "v1/task")
	public Map<String, Object> goToUpdateTimedTask(@RequestBody TimedTask timedTask) {
		this.timedTaskServiceImpl.updateTimedTask(timedTask);
		return MapUtil.ok(200,"Timed task status modified successfully");
	}
	
	@DeleteMapping(value = "v1/task/{id}")
	public Map<String, Object> goToDeleteTimedTaskById(@PathVariable Integer id) {
		this.timedTaskServiceImpl.deleteTimedTaskById(id);
		return MapUtil.ok(200,"The timed task numbered '%d' was deleted successfully".formatted(id));
	}
}
