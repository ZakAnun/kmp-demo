import SwiftUI
import Shared
import UIKit

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        SharedFeatureViewControllerKt.SharedFeatureViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    init() {
        SharedFeatureBootstrapKt.installHostContext(
            shopId: "sample-shop-001",
            userId: "sample-user"
        )
    }

    var body: some View {
        ComposeView()
            .ignoresSafeArea()
            .onDisappear {
                SharedFeatureBootstrapKt.clearHostContext()
            }
    }
}
