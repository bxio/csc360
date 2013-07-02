/* Test basic DELETE of one file. */

import uvic.posix.*;
import uvic.disk.*;

class TestDelete extends uvic.posix.Thread
{
	static void main(String args[])
	{
		DiskFCFS disk = new DiskFCFS(0);
		disk.load(0);
		disk.spin(REAL_TIME);
		
		FileSystem fs = new FileSystem(disk);
	
		FileRequester req = new FileRequester(fs, "D1", 0, null, FileRequester.DELETE_FILE);
		req.start(REAL_TIME);
		
		set(REAL_TIME);
		// Keep yielding until all requester threads are finished.
		while (uvic.posix.Thread.count() > 3)
		{
			yield();
		}
		fs.shutdown();
		// Keep yielding until file system is finished.
		while (uvic.posix.Thread.count() > 2)
		{
			yield();
		}
		disk.shutdown();
	}	
}
