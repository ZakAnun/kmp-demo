import SwiftUI
import Shared
import UIKit

struct ContentView: View {
    @State private var showFeature = false

    var body: some View {
        VStack(spacing: 16) {
            Text("存量 iOS 宿主")
                .font(.title2)
            Button("打开 KMP 共享页（注入 HostContext）") {
                SharedFeatureBootstrapKt.installHostContext(
                    shopId: "host-shop-10086",
                    userId: "host-user-001"
                )
                showFeature = true
            }
        }
        .sheet(isPresented: $showFeature, onDismiss: {
            SharedFeatureBootstrapKt.clearHostContext()
        }) {
            FeatureView()
                .ignoresSafeArea()
        }
    }
}

struct FeatureView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        SharedFeatureViewControllerKt.SharedFeatureViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
