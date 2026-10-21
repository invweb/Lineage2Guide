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
        let viewController = ComposeViewController(factory: IOSComposeUIFactory.self)
        window.rootViewController = viewController
        self.window = window
        window.makeKeyAndVisible()
        return true
    }
}

// MARK: - Compose UI Factory
@MainActor
class IOSComposeUIFactory: ComposeUIFactory {
    func composeUI() -> UIViewController {
        let hostingController = UIHostingController(rootView: ComposeSwiftUI())
        return hostingController
    }
}

// MARK: - SwiftUI Wrapper for Compose
struct ComposeSwiftUI: View {
    var body: some View {
        Text("iOS App with Compose Multiplatform")
            .onAppear {
                // This will be replaced with Compose UI
                // For now, show a placeholder
            }
    }
}
