import UIKit
import SwiftUI
import shared

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Initialize Koin for iOS
        IOSAppKoinInitializer().initialize()
        
        let window = UIWindow(frame: UIScreen.main.bounds)
        let viewController = IOSViewController()
        window.rootViewController = viewController
        self.window = window
        window.makeKeyAndVisible()
        return true
    }
}
