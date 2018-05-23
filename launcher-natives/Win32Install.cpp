extern "C"{
#include <windows.h>
};
#include <string>
#include <InstallNatives.hpp>

using std::string;

bool requestForPermission(const string& msg){
  int val = MessageBox(NULL,msg.c_str(),"Sentry Launcher Requires your Permission",MD_OKCANCEL|MB_ICONQUESTION);
  return val==MB_OK;
}

void installRegisterProtocol(const char* launcherPath){
  string cmd = launcherPath;
  cmd+=" -url"
  if(!requestForPermission("Is it ok to register SentryGameEngine as the default handler for sentry: urls?"))
    return;
    
}
