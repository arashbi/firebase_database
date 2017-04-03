#import "FirebaseDatabasePlugin.h"

#import <Firebase/Firebase.h>

@interface NSError(FlutterError)
@property (readonly, nonatomic) FlutterError *flutterError;
@end

@implementation NSError(FlutterError)
- (FlutterError *)flutterError {
  return [FlutterError errorWithCode:[NSString stringWithFormat:@"Error %d", self.code]
                             message:self.domain
                             details:self.localizedDescription];
}
@end

@implementation FirebaseDatabasePlugin {
}

- (instancetype)initWithFlutterView:(FlutterViewController *)flutterView {
  self = [super init];
  if (self) {
    //[FIRApp configure];
    FlutterMethodChannel *channel = [FlutterMethodChannel
                                     methodChannelWithName:@"firebase_database"
                                     binaryMessenger:flutterView];
    [channel setMethodCallHandler:^(FlutterMethodCall *call,
                                    FlutterResultReceiver result) {
      if ([@"DatabaseReference#set" isEqualToString:call.method]) {
        NSDictionary *data = call.arguments[0];
//        [[[FIRDatabase database].reference childByAutoId] updateChildValues:data
//         withCompletionBlock:^(NSError * _Nullable error, FIRDatabaseReference * _Nonnull ref) {
//             if (error != nil) {
//               result(error.flutterError);
//             } else {
//               result(nil);
//             }
//         }];
      }
    }];
  }
  return self;
}

@end
