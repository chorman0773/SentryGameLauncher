extern "C"{
#include <windows.h>
};
#include <string>


using std::string;

bool requestForPermission(const string& msg){
  int val = MessageBox(NULL,msg.c_str(),"Sentry Launcher Requires your Permission",MD_OKCANCEL|MB_ICONQUESTION);
  return val==MB_OK;
}

void 

extern "C" extern void installRegisterProtocol(const char* protocolHandleCSID,const char* protocolHandleProcId,const char* protocolHandleCommand){
  HKEY currUser = HKEY_CURRENT_USER;
  HKEY software;
  HKEY ms;
  HKEY winsearch;
  HKEY protocolHandlers;
  registerPid(protocolHandleProcId,protocolHandleCommand);
  
  if(!requestForPermission("Is it ok to register SentryGameEngine as the default handler for sentry: urls?"))
    return;
  RegCreateKeyEx(currUser,"SOFTWARE",0,NULL,0,KEY_ALL_ACCESS|KEY_WOW64_64KEY,NULL,&software,NULL);
  RegCreateKeyEx(software,"Microsoft",0,NULL,0,KEY_ALL_ACCESS|KEY_WOW64_64KEY,NULL,&ms,NULL);
  RegCreateKeyEx(ms,"Windows Search",0,NULL,0,KEY_ALL_ACCESS|KEY_WOW64_64KEY,NULL,&winSearch,NULL);
  RegCreateKeyEx(winSearch,"Protocol Handlers",0,NULL,0,KEY_ALL_ACCESS|KEY_WOW64_64KEY,NULL,&protocolHandlers,NULL);
  RegSetValueEx(protocolHandlers,"sentry",REG_SZ,cmd.c_str(),cmd.length()+1);
}

 extern "C" extern void installRegisterFileExtension(const char* extensionHandleProcId,const char* extensionHandleCommand){
   HKEY classes = HKEY_CLASSES_ROOT;
   
 }
