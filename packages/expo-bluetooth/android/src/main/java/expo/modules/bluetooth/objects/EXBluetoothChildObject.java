package expo.modules.bluetooth.objects;

import android.os.Bundle;

import expo.modules.bluetooth.BluetoothConstants;
import expo.modules.bluetooth.BluetoothError;
import expo.modules.bluetooth.BluetoothModule;
import expo.modules.bluetooth.helpers.UUIDHelper;

public class EXBluetoothChildObject extends EXBluetoothObject {

  public EXBluetoothChildObject(Object nativeData, Object parent) {
    super(nativeData, parent);
  }

  @Override
  public String getID() {
    return this.getParent().getID() + "|" + UUIDHelper.fromUUID(getUUID());
  }

  @Override
  public Peripheral getPeripheral() {
    return getParent().getPeripheral();
  }

  @Override
  public Bundle toJSON() {
    Bundle output = super.toJSON();
    output.putString(BluetoothConstants.JSON.UUID, UUIDHelper.fromUUID(getUUID()));
    return output;
  }

  protected Bundle sendEvent(String transaction, String eventName, int status) {
    Bundle output = new Bundle();
    output.putString(BluetoothConstants.JSON.TRANSACTION_ID, transactionIdForOperation(transaction));
    output.putBundle(BluetoothConstants.JSON.PERIPHERAL, getPeripheral().toJSON());
    output.putBundle(BluetoothConstants.JSON.ERROR, BluetoothError.errorFromGattStatus(status));
    return output;
  }
}