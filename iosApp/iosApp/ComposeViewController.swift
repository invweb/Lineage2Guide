import UIKit
import SwiftUI
import shared

@MainActor
public protocol ComposeUIFactory {
    init()
    func composeUI() -> UIViewController
}

public func ComposeUIViewController<Factory: ComposeUIFactory>(factory: Factory.Type) -> UIViewController {
    return ComposeViewController(factory: factory)
}

public class ComposeViewController: UIViewController {
    private let factory: ComposeUIFactory.Type
    
    public init<Factory: ComposeUIFactory>(factory: Factory.Type) {
        self.factory = factory
        super.init(nibName: nil, bundle: nil)
    }
    
    public required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        let composeUI = factory.init()
        let content = composeUI.composeUI()
        addChild(content)
        view.addSubview(content.view)
        content.view.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            content.view.topAnchor.constraint(equalTo: view.topAnchor),
            content.view.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            content.view.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            content.view.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
        content.didMove(toParent: self)
    }
}
