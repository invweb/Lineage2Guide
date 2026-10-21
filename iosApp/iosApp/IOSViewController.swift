import UIKit
import SwiftUI
import shared

class IOSViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.systemBackground
        
        // Create Compose View
        let composeView = ComposeView(viewController: self)
        composeView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(composeView)
        
        NSLayoutConstraint.activate([
            composeView.topAnchor.constraint(equalTo: view.topAnchor),
            composeView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            composeView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            composeView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
        
        // Set Compose content
        composeView.setContent {
            IOSApp()
        }
    }
}
