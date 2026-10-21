import UIKit
import SwiftUI
import shared

struct ComposeRepresentable: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return ComposeViewController(factory: ComposeUI.init)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

@MainActor
public protocol ComposeUI {
    init()
    func composeUI() -> UIViewController
}

public func ComposeUIViewController<Content: ComposeUI>(@ContentBuilder content: @escaping () -> Content) -> UIViewController {
    let composeUI = content()
    return composeUI.composeUI()
}

@MainActor
public protocol ComposeUIFactory {
    init()
    func composeUI() -> UIViewController
}

public func ComposeUIViewController<Factory: ComposeUIFactory>(factory: Factory.Type) -> UIViewController {
    return ComposeViewController(factory: factory)
}

public class ComposeViewController: UIViewController {
    public init<Factory: ComposeUIFactory>(factory: Factory.Type) {
        super.init(nibName: nil, bundle: nil)
    }
    
    public required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

@MainActor
public protocol ComposeUIViewControllerProtocol {
    init()
}

@MainActor
public func ComposeUIViewController<Content: ComposeUIViewControllerProtocol>(@ContentBuilder content: @escaping () -> Content) -> UIViewController {
    let contentInstance = content()
    return UIViewController()
}
