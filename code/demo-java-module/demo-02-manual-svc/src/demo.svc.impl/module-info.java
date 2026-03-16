module demo.svc.impl {
	requires demo.svc;
	// exports org.example.svc.impl; // ❗
	provides org.example.svc.Service
	    with org.example.svc.impl.ServiceImpl; // ❗
}