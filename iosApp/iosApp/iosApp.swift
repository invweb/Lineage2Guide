import UIKit
import Combine

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let window = UIWindow(frame: UIScreen.main.bounds)
        let viewController = ComposeViewController(factory: ComposeUI.init)
        window.rootViewController = viewController
        self.window = window
        window.makeKeyAndVisible()
        return true
    }
}
