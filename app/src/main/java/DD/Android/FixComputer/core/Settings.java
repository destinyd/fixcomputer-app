package DD.Android.FixComputer.core;


public class Settings {
	public boolean isNotifi = true
            ,isSoundNotifi = true
            ,isShockNotifi = true
            ,isLightNotifi = true
            ,isNotDisturb = false;

	protected static Settings _factory = null;

	public static Settings getFactory() {
		if (_factory == null)
			_factory = new Settings();
		return _factory;
	}

	public static void setFactory(Settings s) {
		_factory = s;
	}

}
