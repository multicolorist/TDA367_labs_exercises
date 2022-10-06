import { LitElement, html } from "lit-element";

const geoOptions = {
  enableHighAccuracy: false,
  timeout: 5000,
  maximumAge: 0
};

class GeoLocation extends LitElement {
    private $server?: GeoLocationServerInterface;

    render() {
        return html``;
    }

    private _geoSuccess(position) {
        // Set attribute
        this.setAttribute("latitude", position.coords.latitude);
        this.setAttribute("longitude", position.coords.longitude);
        this.$server!.onUpdate();
    }

    private _geoError(error) {
        // Set attribute

        this.$server!.onError();
    }

    updateLocation() {
        navigator.geolocation.getCurrentPosition(this._geoSuccess, this._geoError, geoOptions);
    }

    function GeoLocation() {
        var id = navigator.geolocation.watchPosition(this._geoSuccess, this._geoError, geoOptions);
    }
}

interface GeoLocationServerInterface {
    onUpdate(): void;
}

customElements.define("geolocation", GeoLocation);
