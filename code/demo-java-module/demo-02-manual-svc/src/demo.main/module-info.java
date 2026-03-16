module demo.main {
	// 不用use/provide关键字
	requires demo.svc;
	// requires demo.svc.impl; // ❗
	uses org.example.svc.Service; // ❗
}