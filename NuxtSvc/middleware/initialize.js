export default function({ store, redirect, route, error }) {
  store.dispatch('repository/fetchDocument').catch(err => {
    // eslint-disable-next-line no-console
    console.log(err);
  });
}
